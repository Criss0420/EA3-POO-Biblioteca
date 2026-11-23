# EA3 – Herencia y polimorfismo: Sistema de gestión de biblioteca (Java)

Programación Orientada a Objetos – IU Digital de Antioquia
Autor: Cristian Camilo Londoño Vargas

## Jerarquías
- `Libro` (abstracta) → `LibroGeneral`, `LibroReferencia`, `LibroDigital`
- `Usuario` (abstracta) → `Estudiante`, `Docente`
- `Exception` → `PrestamoException`
- `Prestamo` y `Biblioteca` se relacionan por asociación/composición.

## Polimorfismo
- **Sobrescritura:** `calcularDiasPrestamo()`, `getValorMultaDiaria()`, `getTipo()`, `esRestringido()`,
  `estaDisponible()`, `marcarPrestado()`, `marcarDisponible()`, `mostrarInformacion()`,
  `getDiasAdicionales()`, `puedeLlevarRestringidos()`, `getTipoUsuario()`.
- **Sobrecarga:** `prestarLibro(...)` (3 versiones), `devolverLibro(...)` (2), `buscarPorAutor(...)` (2),
  `puedePrestar(...)` (2), `calcularMulta(...)` (2), constructores de `Estudiante` (2).

## Compilar y ejecutar (Java 17+)
```
javac -encoding UTF-8 -d out src/biblioteca/*.java
java -cp out biblioteca.Main
```
La salida esperada está en `salida.txt`. El diagrama de clases está en `diagrama/`.

**En Windows (CMD o PowerShell):** si las tildes se ven mal en la consola, ejecuta primero `chcp 65001`
para usar UTF-8. El archivo `salida.txt` está guardado en UTF-8 y se abre bien en el Bloc de notas, VS Code o NetBeans.
