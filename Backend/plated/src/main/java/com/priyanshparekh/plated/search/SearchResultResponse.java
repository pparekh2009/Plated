package com.priyanshparekh.plated.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResultResponse {

    private List<UserSearchItemDto> users;
    private List<RecipeSearchItemDto> recipes;

}
