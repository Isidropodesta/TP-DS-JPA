package Main;
import Entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class PersistenceApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();


            Factura factura1 = Factura.builder().
                    fecha("14/04/2017").
                    numero(4).
                    total(20).
                    build();

            Factura factura2 = Factura.builder().
                    fecha("27/02/2017").
                    numero(5).
                    total(126).
                    build();

            List<Factura> facturas = new ArrayList<Factura>();
            facturas.add(factura1);
            facturas.add(factura2);

            Domicilio domicilio1 = Domicilio.builder().
                    nombreCalle("Viamonte").
                    numero(87).
                    build();

            Cliente cliente1 = Cliente.builder().
                    nombre("Alejandro").
                    apellido("Perez").
                    dni(33455298).
                    build();

            factura1.setCliente(cliente1);
            factura2.setCliente(cliente1);
            cliente1.setDomicilio(domicilio1);
            cliente1.setFacturas(facturas);

            DetalleFactura detalle1 = DetalleFactura.builder().
                    cantidad(28).
                    subtotal(15).
                    build();

            DetalleFactura detalle2 = DetalleFactura.builder().
                    cantidad(58).
                    subtotal(84).
                    build();

            List<DetalleFactura> detalles = new ArrayList<DetalleFactura>();
            detalles.add(detalle1);
            detalles.add(detalle2);
            factura1.setDetalles(detalles);

            Articulo articulo1 = Articulo.builder().
                    cantidad(6).
                    denominacion("Cocina").
                    precio(31).
                    build();

            detalle1.setFactura(factura1);
            detalle2.setFactura(factura1);
            detalle1.setArticulo(articulo1);
            detalle2.setArticulo(articulo1);

            Categoria categoria1 = Categoria.builder().
                    denominacion("Escritorio").
                    build();

            List<Articulo> articulos = new ArrayList<Articulo>();
            List<Categoria> categorias = new ArrayList<Categoria>();
            articulos.add(articulo1);
            categorias.add(categoria1);
            articulo1.setCategorias(categorias);
            categoria1.setArticulos(articulos);

            em.persist(factura1);
            /*em.persist(factura2);
            em.persist(domicilio1);
            em.persist(cliente1);
            em.persist(detalle1);
            em.persist(detalle2);
            em.persist(articulo1);
            em.persist(categoria1);*/


            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
}
