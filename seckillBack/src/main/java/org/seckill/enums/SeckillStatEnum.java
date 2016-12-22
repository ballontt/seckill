package org.seckill.enums;
/**
 * Created by ballontt on 2016/12/13.
 */
public enum SeckillStatEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATE_REWRITE(-3,"数据篡改");

    private int state;
    private String info;

    SeckillStatEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }

    public int getState() {
        return state;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "SeckillStatEnum{" +
                "state=" + state +
                ", info='" + info + '\'' +
                '}';
    }

    public static SeckillStatEnum stateOf(int state) {
        for(SeckillStatEnum seckillStatEnum : SeckillStatEnum.values()) {
            if(seckillStatEnum.getState() == state) {
                return seckillStatEnum;
            }
        }
        return null;
    }
}
