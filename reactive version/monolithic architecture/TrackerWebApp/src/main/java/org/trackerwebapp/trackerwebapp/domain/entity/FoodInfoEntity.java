package org.trackerwebapp.trackerwebapp.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "info_foods")
@Data
public class FoodInfoEntity {

  @Id
  private String id;
  @Column("info")
  private String info;
  @Column("more_info")
  private String moreInfo;
  @Column("picture")
  private String picture;
  @Column("food_id")
  private String foodId;

  public FoodInfoEntity() {
    this.id = UUID.randomUUID().toString();
  }
}
