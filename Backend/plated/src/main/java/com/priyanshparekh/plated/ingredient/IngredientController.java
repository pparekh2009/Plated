package com.priyanshparekh.plated.ingredient;

import com.priyanshparekh.plated.MessageResponse;
import com.priyanshparekh.plated.ingredient.dto.AddIngredientsRequest;
import com.priyanshparekh.plated.ingredient.dto.IngredientDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/ingredients")
    public ResponseEntity<MessageResponse> addIngredients(@RequestBody @Valid AddIngredientsRequest addIngredientsRequest) {
        ingredientService.addIngredients(addIngredientsRequest);
        return new ResponseEntity<>(new MessageResponse("Ingredients Added"), HttpStatus.CREATED);
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientDto>> getIngredients() {
        return ResponseEntity.ok(ingredientService.getIngredients());
    }
}
