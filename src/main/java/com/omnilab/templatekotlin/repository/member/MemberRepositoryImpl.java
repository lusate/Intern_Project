package com.omnilab.templatekotlin.repository.member;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryJpql{

    private final EntityManager em;


}
