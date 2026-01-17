package com.placeflow.driveservice.dto;

import com.placeflow.driveservice.entity.RoundType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterviewRoundRequest {

    private Integer roundOrder;
    private String name;
    private RoundType type;
}

