package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "/members/createMemberForm";
    }

    //form의 valid 어노테이션에 맞게 검증해줌
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { //BindingResult 오류 발생시 여기에 코드 담겨서 실행됨.

        //@NotEmpty등 검증 만족 못한 인풋있으면, 에러 결과가 BindingResult에 담김.
        if (result.hasErrors()) { //에러를 인지하고 활용할 수 있게됨
            return "/members/createMemberForm"; //다시 등록 폼으로 이동
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "/members/memberList";
    }

}
