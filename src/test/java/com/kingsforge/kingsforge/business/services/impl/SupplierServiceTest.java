package com.kingsforge.kingsforge.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kingsforge.kingsforge.business.entity.Supplier;
import com.kingsforge.kingsforge.persistance.SupplierRepository;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {

    // Creem el mock object de SupplierRepository que utilitzarem en els tests
    @Mock
    private SupplierRepository repository;

    // Indiquem que s'utilitzaran els mocks en la classe ServiceServiceImpl
    @InjectMocks
    private SupplierServiceImpl service;

    // Supplier que utilitzarem en els tests
    private Supplier Supplier1;
    private Supplier Supplier2;
    private Supplier Supplier3;

    @BeforeEach
    void setup() {
        this.Supplier1 = new Supplier(1, 612345678,
                "Como tantas grandes historias, Broken Wand nace de un grupo de amigos uniendo su pasión en una gran aventura. En este caso, el rol en vivo o LARP. Juntos, decidieron crear aventuras y mundos en los que otros aventureros pudiesen vivir otras vidas, y formaron un equipo de guionistas, artesanos y organizadores que en 2017 darían luz al primer rol en vivo de Broken Wand, inspirado en el mundo de The Witcher. Los aventureros unieron sus fuerzas una vez más, y en 2018 se realizó la segunda parte de dicho vivo. Actualmente Broken Wand dedica cuerpo y alma a dar vida en su taller a artilugios, armas, criaturas y cualquier clase de objeto que cualquier jugador, master, o sencillamente coleccionista pueda necesitar para crear sus propias aventuras, así que recuerda: ¡Cada daga, espada u objeto que crean expresa su deseo de ser parte de vuestros personajes, de vuestras aventuras y batallas!",
                "Broken Wand",
                "https://www.brokenwand.es/", 46229, "Polígono Industrial Codonyers", 4, "Picassent", "España");
        this.Supplier2 = new Supplier(2, 623456789,
                "Rawblade es una compañía B2B, centrada en el mundo de la recreación medieval y el Rol en Vivo. Rawblade intenta contribuir al mercado de las conocidas artesanías españolas, con una larga tradición de tratado de pieles y cuero, así como de aceros. Inspirados por el famoso acero toledano, Rawblade desarrolla sets de armaduras y cotas de malla. El equipo humano tras Rawblade está formado por la experiencia obtenida en Evil Tailors, así como varias plataformas que lo han estado integrando a lo largo del tiempo. El trabajo duro, en diseño y en ideas que conforman el catálogo de Rawblade, es el resultado de la inspiración en el mundo de la fantasía medieval que les ha acompañado desde la infancia. Ofrecen Supplieros de calidad a un precio competitivo e intentan utilizar Supplieros y proveedores españoles. Todo el cuero es 100% hecho en España, añadiendo el sello de calidad a las creaciones. Sus creaciones de espuma, ropa y placas de acero están diseñadas y hechas en España e India.",
                "Rawblade",
                "https://www.rawblade.com/", 3802, "Els Plans", 5, "Alcoy", "España");
        this.Supplier3 = new Supplier(3, 634567890,
                "Fundado en 2004 en las frías llanuras del este de Canadá, Calimacil se ha convertido en un proveedor de referencia mundial de material de Rol en Vivo y recreación histórica. Sus diseños lujosamente decorados, están inspirados por el lore marcial de la historia, las leyendas y las historias tomadas directamente de la fantasía heróica. Sus Supplieros se fabrican por un grupo de artesanos y jugadores apasionados y luchadores de artes marciales. Jugar es una parte fundamental de los valores de la compañía ya que centra la energía creativa y les lleva a crear nuevos y emocionantes modelos de armas.",
                "Calimacil",
                "https://calimacil.com/", 46960, "Rue Deschaillons", 1150, "Sherbrooke", "Canadá");
    }

    @DisplayName("Prova de listSuppliers() exist")
    @Test
    public void shouldReturnSupplierList() throws RuntimeException {

        ArrayList<Supplier> suppliers = new ArrayList<>(List.of(Supplier1, Supplier2, Supplier3));

        // Configurem les accions del objecte Mock
        when(repository.listSuppliers()).thenReturn(suppliers);

        List<Supplier> actual = service.listSuppliers();

        assertEquals(suppliers, actual);
    }

    @DisplayName("Prova de getSupplierById(int) exist")
    @Test
    public void givenId_ShouldReturnThatSupplier() throws RuntimeException {

        // Configurem les accions del objecte Mock
        when(repository.getSupplierById(1, "_es")).thenReturn(Supplier1);
        when(repository.getSupplierById(2, "_es")).thenReturn(Supplier2);
        when(repository.getSupplierById(3, "_es")).thenReturn(Supplier3);

        Supplier actual1 = service.getSupplierById(1, "_es");
        assertEquals(Supplier1, actual1);

        Supplier actual2 = service.getSupplierById(2, "_es");
        assertEquals(Supplier2, actual2);

        Supplier actual3 = service.getSupplierById(3, "_es");
        assertEquals(Supplier3, actual3);

    }

    @DisplayName("Prova de getSuppliersById(int) not exist")
    @Test
    public void givenNonExistingFatherId_ShouldThrowException() throws RuntimeException {

        // Configurem les accions del objecte Mock
        when(repository.getSupplierById(-1,"_es")).thenThrow(
            RuntimeException.class
        );

        assertThrows(
            RuntimeException.class,
            () -> service.getSupplierById(-1,"_es"),
            "Expected RuntimeException exception, but it was not thrown."
        );
    }

}
