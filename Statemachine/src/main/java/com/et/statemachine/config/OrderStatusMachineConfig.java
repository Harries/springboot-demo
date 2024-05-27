package com.et.statemachine.config;

import com.et.statemachine.state.OrderStatusChangeEventEnum;
import com.et.statemachine.state.OrderStatusEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @description: order statemachine
 */
@Configuration
@EnableStateMachine
public class OrderStatusMachineConfig extends StateMachineConfigurerAdapter<OrderStatusEnum, OrderStatusChangeEventEnum> {

    /**
     * configure state
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderStatusChangeEventEnum> states) throws Exception {
        states.withStates()
                .initial(OrderStatusEnum.WAIT_PAYMENT)
                .end(OrderStatusEnum.FINISH)
                .states(EnumSet.allOf(OrderStatusEnum.class));
    }

    /**
     * configure state transient  with event
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderStatusChangeEventEnum> transitions) throws Exception {
        transitions.withExternal().source(OrderStatusEnum.WAIT_PAYMENT).target(OrderStatusEnum.WAIT_DELIVER)
                .event(OrderStatusChangeEventEnum.PAYED)
                .and()
                .withExternal().source(OrderStatusEnum.WAIT_DELIVER).target(OrderStatusEnum.WAIT_RECEIVE)
                .event(OrderStatusChangeEventEnum.DELIVERY)
                .and()
                .withExternal().source(OrderStatusEnum.WAIT_RECEIVE).target(OrderStatusEnum.FINISH)
                .event(OrderStatusChangeEventEnum.RECEIVED);
    }
}