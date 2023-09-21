package com.omnilab.templatekotlin.service;

import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.member.MemberDto;
import com.omnilab.templatekotlin.domain.review.Review;
import com.omnilab.templatekotlin.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@EnableCaching //
//public class MemberService implements UserDetailsService {
public class MemberService { // loadUserByUsername 쓰지 않고 하기 위해 해봄

    private EntityManager em;
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    @Transactional // DB에 데이터를 변경하는 경우 추가해준다.
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    // 회원 전체 조회
    @Modifying(clearAutomatically = true)
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }



    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public void validateDuplicateMember(Member member) {
        //EXCEPTION
        Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());

        if (!findMember.isEmpty()) { //비어있지 않으면 (잘못 된 것)
            throw new IllegalStateException("이미 존재하는 회원입니다!!!!!");
        }

    }

    // 아이디 중복
    @Transactional
    public int idCheck(String loginId) throws Exception {
        int memberByLoginId = memberRepository.countByLoginId(loginId);
        return memberByLoginId;
    }




    /**
     * 페이징
     **/
    //@Cacheable("members") // @Cacheable -> 캐시에 데이터가 없을 경우에는 기존의 로직을 실행한 후에 캐시에 데이터를 추가하고, 캐시에 데이터가 있으면 캐시의 데이터를 반환한다
    public Page<MemberDto> findAll(Pageable pageable){
        return memberRepository.findAll(pageable).map(m -> new MemberDto(m));
    }


    //회원 정보 수정
    @Transactional
    public void updateMember(Long id, String password, String username, String city, String street, String detail) {
        Optional<Member> findMember = memberRepository.findById(id);
        Member member = findMember.orElseThrow(() -> new IllegalStateException("해당하는 멤버가 존재하지 않습니다."));
        member.update(password,username,city,street,detail);
    } //수정한 멤버에 대한 로그인을 할 때 -> 즉 다시 로그인 시에 수정한 정보와 데이터가 맞는지 확인.



//    username이라는 회원 아이디와 같은 식별 값으로 회원 정보를 조회하는 곳
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Member> member = memberRepository.findByLoginId(username);
//
//
//        if (member == null) {
//            throw new UsernameNotFoundException(username);
//        }
//
//        else{
//            return new User(member.get().getUsername(),member.get().getPassword(), new ArrayList<>());
//        }
//    }



//    loadUserByUsername 메서드 이용하지 않고 직접 DB의 user 정보를 가져오도록 구현 -> AuthProvider 작성

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public Optional<Member> findUserByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId);
    }




    /**
     * 데이터를 넣은 엑셀 다운
     */
    public void excelDownloadResponse(HttpServletResponse response) {
        List<Member> memberList = memberRepository.findAll();

        try{
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("회원 목록");

            //숫자 포맷은 아래 numberCellStyle을 적용시킬 것이다다
            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

            //파일명
            final String fileName = "회원 목록";

            //헤더
            final String[] header = {"이름", "도시", "주소", "상세 주소", "권한"};
            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            //바디
            for (int i = 0; i < memberList.size(); i++) {
                row = sheet.createRow(i + 1);  //헤더 이후로 데이터가 출력되어야하니 +1

                Member member = memberList.get(i);

                Cell cell = null;
                cell = row.createCell(0);
                cell.setCellValue(member.getUsername());

                cell = row.createCell(1);
                cell.setCellValue(member.getAddress().getCity());

                cell = row.createCell(2);
                cell.setCellValue(member.getAddress().getStreet());

                cell = row.createCell(3);
                cell.setCellValue(member.getAddress().getDetail());

                cell = row.createCell(4);
                cell.setCellStyle(numberCellStyle);      //숫자포맷 적용
                cell.setCellValue(member.getRole());
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
}
