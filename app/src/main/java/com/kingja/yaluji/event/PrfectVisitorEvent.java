package com.kingja.yaluji.event;


import com.kingja.yaluji.model.entiy.Visitor;

/**
 * Description:TODO
 * Create Time:2018/7/3 16:07
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PrfectVisitorEvent extends Visitor {

    public PrfectVisitorEvent(Visitor visitor) {
        this.id=visitor.getId();
        this.name=visitor.getName();
        this.mobile=visitor.getMobile();
        this.idcode=visitor.getIdcode();
        this.isdefault=visitor.getIsdefault();
    }
}
