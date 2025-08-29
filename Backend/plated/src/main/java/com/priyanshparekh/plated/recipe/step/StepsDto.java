package com.priyanshparekh.plated.recipe.step;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StepsDto {

    private int stepNo;
    private String step;

}
