package com.kakaopay.homework.internetbanking.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInternetBankingInfo is a Querydsl query type for InternetBankingInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInternetBankingInfo extends EntityPathBase<InternetBankingInfo> {

    private static final long serialVersionUID = 2135998612L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInternetBankingInfo internetBankingInfo = new QInternetBankingInfo("internetBankingInfo");

    public final QDevice device;

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QInternetBankingInfo(String variable) {
        this(InternetBankingInfo.class, forVariable(variable), INITS);
    }

    public QInternetBankingInfo(Path<? extends InternetBankingInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInternetBankingInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInternetBankingInfo(PathMetadata metadata, PathInits inits) {
        this(InternetBankingInfo.class, metadata, inits);
    }

    public QInternetBankingInfo(Class<? extends InternetBankingInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.device = inits.isInitialized("device") ? new QDevice(forProperty("device")) : null;
    }

}

