package com.kakaopay.homework.internetbanking.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QInternetBankingInfoId is a Querydsl query type for InternetBankingInfoId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QInternetBankingInfoId extends BeanPath<InternetBankingInfoId> {

    private static final long serialVersionUID = -299698993L;

    public static final QInternetBankingInfoId internetBankingInfoId = new QInternetBankingInfoId("internetBankingInfoId");

    public final StringPath device = createString("device");

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QInternetBankingInfoId(String variable) {
        super(InternetBankingInfoId.class, forVariable(variable));
    }

    public QInternetBankingInfoId(Path<? extends InternetBankingInfoId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInternetBankingInfoId(PathMetadata metadata) {
        super(InternetBankingInfoId.class, metadata);
    }

}

