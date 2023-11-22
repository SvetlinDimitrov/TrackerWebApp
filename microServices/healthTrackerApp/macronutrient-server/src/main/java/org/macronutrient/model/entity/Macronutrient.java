package org.macronutrient.model.entity;

import java.util.List;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class Macronutrient {

    private String name;
    private String description;
    private Double activeState ;
    private Double inactiveState ;
    private List<Pair> functions;
    private List<Pair> sources;
    private List<Pair> types;
    private List<Pair> dietaryConsiderations;

}
