package org.example.domain.food.nutriox_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tags {

  private String item;
  private String measure;

  @JsonProperty("food_group")
  private String foodGroup;

  @JsonProperty("tag_id")
  private String tagId;
}
// "item": "chia seed",
//    "measure": null,
//    "quantity": "1.0",
//    "food_group": 0,
//    "tag_id": 1184
