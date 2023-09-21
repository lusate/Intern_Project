package com.omnilab.templatekotlin.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnilab.templatekotlin.domain.cart.CartItem;
import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.item.ItemDto;
import com.omnilab.templatekotlin.domain.item.ItemType;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.exception.NotCorrespondingException;
import com.omnilab.templatekotlin.repository.item.ItemRepository;
import com.omnilab.templatekotlin.repository.item.ItemSearch;
import com.omnilab.templatekotlin.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;


    @Value("${file.dir}")
    private String fileDir;


    @Transactional
    public void saveItem(Item item, MultipartFile files) throws IOException {

        if (files != null) {
            // 원래 파일 이름
            String origName = files.getOriginalFilename();

            // 파일 이름으로 쓸 uuid 생성
            String uuid = UUID.randomUUID().toString();

            // 확장자 추출(ex : .png)
            String extension = origName.substring(origName.lastIndexOf("."));

            // uuid와 확장자 결합
            String savedName = uuid + extension;

            // 파일을 불러올 때 사용할 파일 경로
            String savedPath = fileDir + savedName;



            // 실제로 로컬에 uuid를 파일명으로 저장
            files.transferTo(new File(savedPath));

            // 데이터베이스에 파일 정보 저장
            item.setFileOriName(origName);
            item.setSaveName(savedName);
            item.setFileUrl(savedPath);

        }

        itemRepository.save(item);

    }


    public Optional<Item> findOne(Long id){
        return itemRepository.findById(id);
    }



    public Page<ItemDto> findAll(ItemSearch itemSearch, Pageable pageable){
        return itemRepository.findAll(itemSearch, pageable).map(ItemDto::new);
    }


    public Page<Item> getBoardList(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }


    public void cancelStockItem(Long itemId) {
        itemRepository.deleteById(itemId); // 한 개 취소

        // deleteById 내부적으로 delete를 호출
        // 넘어온 id값으로 findById를 사용하여 delete에 인자로 넘겨줄 데이터를 조회

        // 넘어온 id 값이 null 인 경우는 EmptyResultDataAccessException 을 발생
    }


    public void updateItem(Long itemId, int stockQuantity) {
        Item item = itemRepository.findById(itemId).get();

        item.setId(itemId);
        item.setStockQuantity(stockQuantity);
    }



    /**
     * 데이터를 넣은 엑셀 다운
     */

    public void getExcelDown(HttpServletResponse response, ItemType itemType) {
        List<Item> itemList;

        if (itemType == null) {
            itemList = itemRepository.findAll();
        } else {
            itemList = itemRepository.findByDtype(String.valueOf(itemType));
        }

        try{
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("상품 목록");

            //숫자 포맷은 아래 numberCellStyle을 적용
            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

            //파일명
            final String fileName = "상품 목록";

            //헤더
            final String[] header = {"NO", "상품 이름", "상품 종류", "상품 가격", "재고"};
            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            //바디
            for (int i = 0; i < itemList.size(); i++) {
                row = sheet.createRow(i + 1);  //헤더 이후로 데이터가 출력되어야하니 +1

                Item item = itemList.get(i);
                createRow(numberCellStyle, row, item);
            }



            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+".xlsx");
            //파일명은 URLEncoder로 감싸주는게 좋다!

            workbook.write(response.getOutputStream());
            workbook.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }



    private void createRow(CellStyle numberCellStyle, Row row, Item item) {
        Cell cell = null;

        cell = row.createCell(0);
        cell.setCellValue(item.getId());

        cell = row.createCell(1);
        cell.setCellValue(item.getName());

        cell = row.createCell(2);
        cell.setCellValue(item.getDtype());

        cell = row.createCell(3);
        cell.setCellValue(item.getPrice());

        cell = row.createCell(4);
        cell.setCellStyle(numberCellStyle);      //숫자포맷 적용
        cell.setCellValue(item.getStockQuantity());
    }

}




//getTotalPages() : 총 페이지 수
//    getTotalElements() : 쿼리 결과물의 전체 데이터 개수
//    getNumber() : 현재 페이지 번호, 요소를 가져온 페이지의 번호
//    getSize() : 페이지 당 데이터 개수
//    hasnext() : 다음 페이지 존재 여부
//    isFirst() : 시작페이지 여부
//    getContent(), get() : 실제 컨텐츠를 가지고 오는 메서드. getContext는 List<Entity> 반환, get()은 Stream<Entity> 반환
//    get TotalPages() : 쿼리를 통해 가져온 요소들을 size크기에 맞춰 페이징하였을 떄 나오는 총 페이지의 개수


// Request.of page(조회하고자 하는 페이지 번호)와 size(한 페이즈 당 조회할 크기)를 기본인자로 사용하며 추가적으로 정렬을 어떤 방식으로 할 것인지를 정할 수 있는 메서드
// PageRequest : 몇 페이지, 한 페이지의 사이즈, Sorting 방법(Option)을 가지고, Repository에 Paging을 요청할 때 사용하는 것
