package org.vitamin.model.dtos;

import lombok.*;
import org.vitamin.model.entity.Pair;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PairView {

    private String key;
    private String value;

    public PairView(Pair entity) {
        this.key = entity.getKey();
        this.value = entity.getValue();
    }

}
