alter table order_ modify column order_status enum(
    'ORDER_RECEIVED',
    'PREPARING',
    'READY_FOR_DELIVERY',
    'NOT_ASSIGNED',
    'ASSIGNED',
    'OUT_FOR_DELIVERY',
    'DELIVERED',
    'CANCELLED');

alter table order_ modify column payment_status enum(
    'PAID',
    'NOT_PAID');