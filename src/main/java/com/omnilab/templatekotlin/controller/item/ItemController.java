package com.omnilab.templatekotlin.controller.item;


import com.omnilab.templatekotlin.controller.SessionConst;
import com.omnilab.templatekotlin.domain.ItemForm.ItemForm;
import com.omnilab.templatekotlin.domain.file.FileStore;
import com.omnilab.templatekotlin.domain.item.*;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.review.*;
import com.omnilab.templatekotlin.exception.NotCorrespondingItemException;
import com.omnilab.templatekotlin.repository.item.ItemSearch;
import com.omnilab.templatekotlin.repository.review.CommentRepository;
import com.omnilab.templatekotlin.service.ItemService;
import com.omnilab.templatekotlin.service.MemberService;
import com.omnilab.templatekotlin.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ItemService itemService;
    private final ReviewService reviewService;
    private final CommentRepository commentRepository;

    private final MemberService memberService;

    @GetMapping("/items/new")
    public String createItemForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                                 @PageableDefault(page = 0, size = 9, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                 @ModelAttribute("itemSearch") ItemSearch itemSearch,
                                 Model model) {

        ItemForm form = new ItemForm();
        form.setMemberId(memberId);
        model.addAttribute("form", form);


        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // 현재 0이면 0 아니면 -1 처리 해서 1부터 시작
        pageable = PageRequest.of(page, 7); //page번째 페이지에서 시작해서 size=8까지
        logger.error("page {} ", page); // 이때+ page {0}


        model.addAttribute("items", itemService.findAll(itemSearch, pageable));
        model.addAttribute("itemSearch", itemSearch);
        model.addAttribute("memberId", memberId);


        /**
         * 잘못 -> 여기서는 페이징을 할 때 1을 클릭하면 url에서 page=1을 보여주지만 실제로는 2page를 보여줌.
         * 즉 0page 부터 시작하는 것.
         * 위에서 반영을 하지 않고 넘어갔기 때문에 밀리게 된 것.
         */
        Page<Item> boardList = itemService.getBoardList(pageable);


        int nowPage = boardList.getPageable().getPageNumber() + 1; // 헌재 페이지
        int startPage = Math.max(1, nowPage - 4);; // 블럭에서 보여줄 시작 페이지
        // 현재 페이지에서 -4를 해줬을 때 1보다 작은 수가 나오면 안되기 때문에 1 혹은 현재페이지 - 4 했을 때 더 큰 값을 쓰기 위해 사용

        int endPage = Math.min(nowPage + 9, boardList.getTotalPages());



        log.info("nowPage = {}", nowPage); // 처음 시작 때 현재 페이지 1
        log.info("startPage = {}", startPage); //시작 페이지 1
        log.info("endPage = {}", endPage);


        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "items/stockList";
    }


    @PostMapping("/items/new/register/book")
    public String createBook(ItemForm form, @RequestParam("files") List<MultipartFile> files) throws IOException {
        Member member = memberService.findOne(form.getMemberId());
        Book book = Book.createBook(member, form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());


        if (files != null && !files.isEmpty()) {
            itemService.saveItem(book, files.get(0));
        } else {
            itemService.saveItem(book, null);
        }

        log.info("get(0) : {}", files.get(0));

        return "redirect:/items";
    }

    @PostMapping("/items/new/register/cloth")
    public String createCloth(ItemForm form, @RequestParam("files") List<MultipartFile> files) throws IOException {
        Member member = memberService.findOne(form.getMemberId());
        Clothes clothes = Clothes.createClothes(member, form.getName(), form.getPrice(), form.getStockQuantity(), form.getBrand(), form.getSize(), form.getColor());


        if (files != null && !files.isEmpty()) {
            itemService.saveItem(clothes, files.get(0));
        } else {
            itemService.saveItem(clothes, null);
        }

        log.info("get(0) : {}", files.get(0));


        return "redirect:/items";
    }


    @GetMapping("/items/new/register")
    public String register(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                           @ModelAttribute("itemSearch") ItemSearch itemSearch,
                           Model model){

        ItemForm form = new ItemForm();
        form.setMemberId(memberId);
        model.addAttribute("form", form);

        return "items/createItemForm";
    }



    @GetMapping("/items")
    public String items(@PageableDefault(page = 0, size=1, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                        @ModelAttribute("itemSearch") ItemSearch itemSearch,
                        Model model) throws IOException {


        /**
         * 이 부분이 itemService에 있었음(수정)
         */
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1); // 현재 0이면 0 아니면 -1 처리 해서 1부터 시작
        pageable = PageRequest.of(page, 1); //page번째 페이지에서 시작해서 데이터 수 size=8까지


        model.addAttribute("items", itemService.findAll(itemSearch, pageable));
        model.addAttribute("itemSearch", itemSearch);
        model.addAttribute("memberId", memberId);



        /**
         * 잘못 -> 여기서는 페이징을 할 때 1을 클릭하면 url에서 page=1을 보여주지만 실제로는 2page를 보여줌.
         * 즉 0page 부터 시작하는 것.
         * 위에서 반영을 하지 않고 넘어갔기 때문에 밀리게 된 것.
         */
        Page<Item> boardList = itemService.getBoardList(pageable);


        int nowPage = boardList.getPageable().getPageNumber() + 1; // 헌재 페이지
        int startPage = Math.max(1, (nowPage - 1) / 10 * 10 + 1); // 블럭에서 보여줄 시작 페이지
        // nowPage가 8이어도 startPage = 1, nowPage가 12면 startPage = 11

        int endPage = Math.min(boardList.getTotalPages(), startPage + 9);

        // "${(boardList.totalPages > startNumber + 9) ? startNumber + 9 : boardList.totalPages}"

        log.info("nowPage = {}", nowPage); // 처음 시작 때 현재 페이지 1
        log.info("startPage = {}", startPage);
        log.info("endPage = {}", endPage);


        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "items/itemCardList";
    }



    //이미지 출력
//    @ResponseBody
//    @GetMapping("/images/{fileId}")
//    public Resource downloadImage(@PathVariable("fileId") Long id) throws MalformedURLException {
//        Item file = itemRepository.findById(id).orElse(null);
//        return new UrlResource("file:" + file.getFileUrl());
//    }


    /*@ResponseBody
    @GetMapping("/images/{saveName}")
    public Resource downloadImage(@PathVariable String saveName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(saveName));
    }*/




    //수정
    @GetMapping("items/new/{id}/edit") //변경 될 수 있기 때문에 {} 안에 넣음
    public String updateItemForm(Model model, @PathVariable Long id) {
//        Item item = itemService.findOne(id).orElseThrow(() -> new NotCorrespondingItemException("해당하는 아이템이 존재하지 않습니다."));
        Item item = itemService.findOne(id).get();

        ItemForm itemForm = new ItemForm();
        itemForm.setStockQuantity(item.getStockQuantity());

        model.addAttribute("itemForm", itemForm);
        return "items/stockList";
    }


    @PostMapping("items/new/{id}/edit")
    public String updateItem(@ModelAttribute("itemForm") ItemForm itemForm, @PathVariable Long id) {

        logger.error("여기 {} {}", id, itemForm.getStockQuantity());

        // itemId를 제대로 주면 수정 가능. 안 줘서 The id must not be null! 에러
        itemService.updateItem(id, itemForm.getStockQuantity());

        return "redirect:/items/new";
    }


    /**
     * 관리자가 재고 삭제
     */
    @GetMapping("/items/new/{itemId}/delete")
    public String cancelStockItem(@PathVariable("itemId") Long itemId,
                                  HttpServletRequest request){

        log.error("로그 {}", itemId);

        itemService.cancelStockItem(itemId);
        return "redirect:/items/new";
    }
    // 자식 객체 데이터를 먼저 삭제해주고 부모 객체 데이터를 삭제



    /**
     * 물품 상세 정보 + 리뷰 작성
     **/
    @GetMapping("/item/info/{itemId}/all")
    public String itemMoreInfo(@PathVariable("itemId") Long itemId,

                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Long memberId,
                               Model model) {

        Item item = itemService.findOne(itemId).orElseThrow(() -> new NotCorrespondingItemException("해당하는 아이템이 존재하지 않습니다."));
        Member findMember = memberService.findOne(memberId);



        if (item instanceof Book) {
            Book book = (Book) item;
            model.addAttribute("item", book);
        } else if (item instanceof Clothes) {
            Clothes clothes = (Clothes) item;
            model.addAttribute("item", clothes);
        }


        List<ReviewDto> reviews = reviewService.getListItem(itemId); // 기존 리뷰들


        // 쿼리 생성
        List<Comment> commentList = commentRepository.findAll();





        model.addAttribute("reviews", reviews);
        model.addAttribute("member", findMember);
        model.addAttribute("commentList", commentList);
//        model.addAttribute("commentList", map);


        return "items/itemMoreInfo";
//        return new ResponseEntity<>(reviewDtoList, HttpStatus.OK);
    }


    //리뷰 등록
    @PostMapping("/item/info/{itemId}") // 리뷰 등록
    public ResponseEntity<Long> addReview(@RequestBody ReviewDto reviewDto){

        Long reviewId = reviewService.register(reviewDto);

        return new ResponseEntity<>(reviewId,HttpStatus.OK);
    }





    //리뷰 수정
    @PutMapping("/item/info/{itemId}/{reviewId}/edit")
    public ResponseEntity<Long> editReview(@PathVariable Long reviewId, @RequestBody ReviewDto reviewDto,
                                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Long memberId){

        Review review = reviewService.findOne(reviewId);

        // 사용자 입장에서 현재 memberId는 2 getMemberId는 1
        log.error("memberId : {}", memberId); // 2
        log.error("review getMemberId() : {}", review.getMember().getId()); // 1



        if(memberId.equals(review.getMember().getId())){
            reviewService.edit(reviewDto);
            log.error("reviewId : {}", reviewId);
            log.error("reviewId : {}", reviewDto.getReviewId());
            return new ResponseEntity<>(reviewId,HttpStatus.OK);

        } else throw new IllegalStateException("타인의 댓글은 수정 불가능합니다.");
    }



    //리뷰 삭제
    @DeleteMapping("/item/info/{itemId}/{reviewId}/remove")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewId){

        reviewService.remove(reviewId);

        return new ResponseEntity<>(reviewId,HttpStatus.OK);
    }






    // 엑셀 다운 컨트롤러 (전부, 검색 별로)
    @PostMapping("/excel/down")
    public void excelDown(HttpServletResponse response, @RequestParam(required = false) ItemType itemType) {

        // param인 itemType이 null일 수 있기 때문에 required = false로 지정.
        logger.error("{}", itemType);


        itemService.getExcelDown(response, itemType);
    }

}



//Resource Resource에 대한 접근을 추상화하기 위한 인터페이스이며 스프링 IoC 컨테이너가 생성될 때, 컨테이너 설정 정보를 담는 파일들을 가져올 때도 사용된다.


/**
 * 여기서 page 0이 반영이 되지 않고 116 라인으로 감.
 */



/*
ResponseEntity : 개발자가 직접 결과 데이터와 HTTP 상태 코드를 직접 제어
HTTP 상태 코드를 전송하고 싶은 데이터와 함께 전송 가능
 */