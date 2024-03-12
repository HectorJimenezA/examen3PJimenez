package ec.edu.espe.producto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService ProductoService;

    public ProductoController(ProductoService ProductoService) {
        this.ProductoService = ProductoService;
    }

    @GetMapping
    public ResponseEntity<List<Productos>> listarProductos() {
        log.info("Obteniendo listado de productos");
        return ResponseEntity.ok(this.ProductoService.listarTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable(name = "id") String id) {
        log.info("Obteniendo producto con ID: {}", id);
        try {
            return ResponseEntity.ok(this.ProductoService.obtenerPorId(id));
        } catch(RuntimeException rte) {
            log.error("Error al obtener producto por ID", rte);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody Producto producto) {
        log.info("Se va a crear el producto: {}", producto);
        try {
            this.ProductoService.crear(producto);
            return ResponseEntity.noContent().build();
        } catch(RuntimeException rte) {
            log.error("Error al crear el producto", rte);
            return ResponseEntity.badRequest().build();
        }
    }
}
