package msg.mortality.mortality.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Mortality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    public Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "male Death Rate")
    private double maleDeathRate;

    @Column(name = "female Death Rate")
    private double femaleDeathRate;
}
