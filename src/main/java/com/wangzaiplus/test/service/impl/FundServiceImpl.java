package com.wangzaiplus.test.service.impl;

import com.wangzaiplus.test.dto.FundDto;
import com.wangzaiplus.test.service.FundService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FundServiceImpl implements FundService {

    @Override
    public Set<FundDto> combine(int[] earnings, Integer year) {

        return null;
    }

    private Set<FundDto> getCombinedFund() {
        return null;
    }

    private static Set<FundDto> intersection(Set<FundDto> set1, Set<FundDto> set2) {
        set1.retainAll(set2);
        return set1;
    }

    public static void main(String[] args) {
        System.out.println(reverse("abcdefg", 2));
        System.out.println(reverse("lrloseumgh", 6));

        System.out.println(reverse2("abcdefg", 2));
        System.out.println(reverse2("lrloseumgh", 6));
    }

    private static String reverse(String s, int n) {
        String start = s.substring(0, n);
        String end = s.substring(n);
        return end + start;
    }

    private static String reverse2(String s, int n) {
        return (s + s).substring(n, n + s.length());
    }

}
