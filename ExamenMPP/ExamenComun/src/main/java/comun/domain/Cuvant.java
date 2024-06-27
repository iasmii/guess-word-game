package comun.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.IDENTITY;

@jakarta.persistence.Entity
@Table(name = "Cuvant")
public class Cuvant implements comun.domain.Entity<Long>{

        @Id
        @GeneratedValue(strategy = IDENTITY)
        private Long id;

        private String litere;

        private String cuvinte;

        public Cuvant() {}

        public Cuvant(String litere, String cuvinte) {
            this.litere = litere;
            this.litere = cuvinte;
        }

        public Cuvant(Long id, String litere, String cuvinte) {
            this.id = id;
            this.litere = litere;
            this.cuvinte = cuvinte;
        }

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public void setId(Long id) {
            this.id = id;
        }

        public String getLitere() {
            return litere;
        }

        public void setLitere(String litere) {
            this.litere = litere;
        }

        public String getCuvinte() {
            return cuvinte;
        }

        public void setCuvinte(String cuvinte) {
            this.cuvinte = cuvinte;
        }
    }