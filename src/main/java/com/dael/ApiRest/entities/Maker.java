package com.dael.ApiRest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Maker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(
            mappedBy = "maker", // Indica que esta relación está mapeada por el campo "maker" en la clase Product.
            cascade = CascadeType.ALL, // Todas las operaciones en cascada se aplicarán a los objetos asociados en la relación.
            fetch = FetchType.LAZY, // Define la estrategia de carga de datos como perezosa.
            orphanRemoval = true // Los objetos Product huérfanos serán eliminados automáticamente de la base de datos.
    )
    @JsonIgnore
    private List<Product> productList = new ArrayList<>();
}
