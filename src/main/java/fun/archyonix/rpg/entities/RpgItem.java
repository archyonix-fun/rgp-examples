package fun.archyonix.rpg.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "rpg_items")
public class RpgItem {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "link", nullable = true)
    private String link;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "rarity", nullable = false)
    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @Column(name = "class_controller", nullable = false)
    private String classController;

    @Column(name = "display_name", nullable = false)
    private String displayName = "Nil";

    @Column(name = "description", nullable = false)
    private String description = "";

    @Column(name = "data", nullable = false)
    private String data;

    enum Category {MATERIAL, WEAPON, ARMOR, OTHER}
    enum Rarity {HUITA, NORMALNO, EPIC, AHUENNO}
}
