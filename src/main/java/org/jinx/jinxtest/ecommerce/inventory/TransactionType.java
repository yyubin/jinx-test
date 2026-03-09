package org.jinx.jinxtest.ecommerce.inventory;

public enum TransactionType {
    PURCHASE_ORDER_RECEIPT,   // 발주 입고
    SALE_RESERVATION,         // 판매 예약
    SALE_DISPATCH,            // 출고
    RETURN_RECEIPT,           // 반품 입고
    ADJUSTMENT_INCREASE,      // 실사 조정 (증가)
    ADJUSTMENT_DECREASE,      // 실사 조정 (감소)
    DAMAGE_WRITE_OFF,         // 파손 폐기
    TRANSFER_IN,              // 창고간 이동 입고
    TRANSFER_OUT              // 창고간 이동 출고
}
