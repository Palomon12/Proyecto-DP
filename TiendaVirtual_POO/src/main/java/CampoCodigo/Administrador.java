package CampoCodigo;

import ClasesDAO.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Administrador {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductoDAO productoDAO = new ProductoDAO();
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final CarritoCompra carritoCompra = new CarritoCompra(1);
    private static Usuario usuarioActual = null;
    private static boolean esAdmin = false;

    public static void main(String[] args) throws SQLException {
        CConexion objetoconexion = new CConexion();
        objetoconexion.getConexion();

        iniciarTienda();

        if (!preguntarSiTieneCuenta()) {
        registrarUsuario();
    } else if (!iniciarSesion()) {
        System.out.println("Error en el inicio de sesión. Saliendo...");
        return;
    }

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    if (esAdmin) {
                        gestionarProductos();
                    } else {
                        verCarrito();
                    }
                }
                case 2 -> {
                    if (esAdmin) {
                        gestionarCategorias();
                    } else {
                        realizarCompra();
                    }
                }
                case 3 -> {
                    if (esAdmin) {
                        gestionarUsuarios();
                    } else {
                        System.out.println("Acceso denegado. Solo para administradores.");
                    }
                }
                case 4 -> {
                    if (esAdmin) {
                        generarInforme();
                    } else {
                        System.out.println("Acceso denegado. Solo para administradores.");
                    }
                }
                case 6 -> {
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                }
                default ->
                    System.out.println("Opción no válida, por favor elija otra.");
            }
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        if (esAdmin) {
            System.out.println("1. Gestionar Productos");
            System.out.println("2. Gestionar Categorías");
            System.out.println("3. Gestionar Usuarios");
            System.out.println("4. Generar Informe de Ventas");
        } else {
            System.out.println("1. Ver Carrito de Compras");
            System.out.println("2. Realizar Compra");
        }
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }


    private static void registrarUsuario() throws SQLException {
        System.out.println("\n--- Registro de Usuario ---");

        // Predicados para validación
        Predicate<String> correoValido = correo -> correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Predicate<String> contraseñaValida = password
                -> password.length() >= 8
                && password.matches(".*[a-z].*")
                && password.matches(".*[A-Z].*")
                && password.matches(".*\\d.*");

        // Consumer para mostrar mensajes
        Consumer<String> mostrarMensaje = System.out::println;

        // Validar el correo
        String correo;
        while (true) {
            System.out.print("Ingrese su correo: ");
            correo = scanner.nextLine().trim();
            if (correoValido.test(correo)) {
                break;
            } else {
                mostrarMensaje.accept("El formato del correo es inválido. Intente nuevamente.");
            }
        }

        // Validar la contraseña
        String contraseña;
        while (true) {
            System.out.print("Ingrese una nueva contraseña: ");
            contraseña = scanner.nextLine().trim();
            if (contraseñaValida.test(contraseña)) {
                break;
            } else {
                mostrarMensaje.accept("La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas y números.");
            }
        }

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        String tipo = "Cliente";
        System.out.print("Ingrese su dirección: ");
        String direccion = scanner.nextLine();

        // Validar el teléfono
        String telefono;
        while (true) {
            System.out.print("Ingrese su teléfono (9 dígitos): ");
            telefono = scanner.nextLine().trim();
            if (telefono.matches("\\d{9}")) {
                break;
            } else {
                mostrarMensaje.accept("El número de teléfono debe tener exactamente 9 dígitos.");
            }
        }

        int telefonoInt = Integer.parseInt(telefono);

        Usuario nuevoUsuario = new Usuario(0, nombre, correo, contraseña, tipo, direccion, telefonoInt);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Registrar usuario
        if (usuarioDAO.crear(nuevoUsuario)) {
            mostrarMensaje.accept("Usuario registrado con éxito.");

            // Recuperar y asignar el usuario actual
            usuarioActual = usuarioDAO.obtenerUsuarioPorEmail(correo);

            if (usuarioActual != null) {
                mostrarMensaje.accept("Bienvenido, " + usuarioActual.getNombre() + "!");
            } else {
                mostrarMensaje.accept("Error al recuperar el usuario recién registrado.");
            }
        } else {
            mostrarMensaje.accept("Error al registrar el usuario.");
        }
    }

    private static boolean preguntarSiTieneCuenta() {
        System.out.print("\n**Cuenta administrador**: \nCorreo: kpalomino233@gmail.com\nContraseña: Mimamamemima123 \n¿Tiene una cuenta? (s/n): ");
        String respuesta = scanner.nextLine();
        return respuesta.equalsIgnoreCase("s");
    }

    private static boolean iniciarSesion() throws SQLException {
        boolean sesionIniciada = false;

        // Predicados para validación
        Predicate<String> correoValido = correo -> correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Predicate<String> contraseñaValida = password
                -> password.length() >= 8
                && password.matches(".*[a-z].*")
                && password.matches(".*[A-Z].*")
                && password.matches(".*\\d.*");

        // BiPredicate para verificar si es administrador
        BiPredicate<String, String> esAdministrador = (correo, password)
                -> correo.equalsIgnoreCase("kpalomino233@gmail.com") && password.equals("Mimamamemima123");

        // Consumer para mostrar mensajes
        Consumer<String> mostrarMensaje = System.out::println;

        while (!sesionIniciada) {
            System.out.print("\n--- Inicio de Sesión ---\n");
            System.out.print("Ingrese su correo: ");
            String correo = scanner.nextLine().trim();
            System.out.print("Ingrese su contraseña: ");
            String password = scanner.nextLine().trim();

            // Validar correo y contraseña
            if (!correoValido.test(correo)) {
                mostrarMensaje.accept("El formato del correo es inválido. Intente nuevamente.");
                continue;
            }
            if (!contraseñaValida.test(password)) {
                mostrarMensaje.accept("La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas y números.");
                continue;
            }

            // Verificar si es administrador
            if (esAdministrador.test(correo, password)) {
                mostrarMensaje.accept("¡Inicio de sesión como administrador exitoso!");
                esAdmin = true;
                sesionIniciada = true;
                break;
            }

            // Si no es administrador, consultar en la base de datos
            Usuario usuario = usuarioDAO.leerPorCredenciales(correo, password);

            if (usuario != null) {
                mostrarMensaje.accept("¡Inicio de sesión exitoso!");
                usuarioActual = usuario; // Asignar el usuario a la variable global
                sesionIniciada = true;
            } else {
                mostrarMensaje.accept("Credenciales incorrectas. Verifique su correo y contraseña.");
            }
        }

        return sesionIniciada;
    }

        private static void iniciarTienda() throws SQLException {

        List<Producto> productos = productoDAO.leerTodos();
        List<Categoria> categorias = categoriaDAO.leerTodos();

        if (productos.isEmpty() && categorias.isEmpty()) {
            System.out.println("Inicializando categorías y productos predeterminados...");

            // Crear categorías con un stream
            List<Categoria> listaCategorias = List.of(
                    new Categoria(1, "Bebidas"),
                    new Categoria(2, "Snacks"),
                    new Categoria(3, "Limpieza"),
                    new Categoria(4, "Lácteos")
            );
            listaCategorias.forEach(categoria -> {
                try {
                    categoriaDAO.crear(categoria);
                } catch (SQLException e) {
                    System.err.println("Error al crear categoría: " + categoria.getNombre() + " - " + e.getMessage());
                }
            });

            // Crear productos con un stream
            List<Producto> listaProductos = List.of(
                    new Producto(1, "Coca Cola 500ml", 3.50, "Bebida gaseosa", 100),
                    new Producto(2, "Inca Kola 1L", 5.00, "Bebida gaseosa", 80),
                    new Producto(3, "Agua Cielo 625ml", 2.00, "Agua mineral", 200),
                    new Producto(4, "Cerveza Pilsen 355ml", 4.50, "Bebida alcohólica", 50),
                    new Producto(5, "Pepsi 1L", 4.00, "Bebida gaseosa", 90),
                    new Producto(6, "Chips Ahoy", 4.50, "Galletas con chips de chocolate", 150),
                    new Producto(7, "Galletas Oreo", 2.50, "Galletas de chocolate rellenas", 120),
                    new Producto(8, "Papitas Lays", 3.00, "Papas fritas", 200),
                    new Producto(9, "Chocolatina Sublime", 1.80, "Chocolatina con maní", 250),
                    new Producto(10, "Chocman", 1.50, "Pastelito cubierto de chocolate", 180),
                    new Producto(11, "Leche Gloria 1L", 4.00, "Leche evaporada", 300),
                    new Producto(12, "Yogurt Gloria 1L", 5.50, "Yogurt de fresa", 100),
                    new Producto(13, "Queso Edam 250g", 10.00, "Queso semi-duro", 60),
                    new Producto(14, "Detergente Ariel 500g", 8.00, "Detergente en polvo", 70),
                    new Producto(15, "Jabón Ace 1L", 6.00, "Jabón líquido", 80)
            );
            listaProductos.forEach(producto -> {
                try {
                    productoDAO.crear(producto);
                } catch (SQLException e) {
                    System.err.println("Error al crear producto: " + producto.getNombre() + " - " + e.getMessage());
                }
            });
        }

        System.out.println("*** Bienvenido a la Tiendita de Don Pepe ***");
    }


    private static void gestionarProductos() throws SQLException {
        System.out.println("\n--- Gestión de Productos ---");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Agregar Producto");
        System.out.println("2. Modificar Producto");
        System.out.println("3. Eliminar Producto");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 ->
                agregarProducto();
            case 2 ->
                modificarProducto();
            case 3 ->
                eliminarProducto();
            default ->
                System.out.println("Opción no válida.");
        }
    }

    private static void agregarProducto() throws SQLException {
        System.out.println("\nIngrese los detalles del nuevo producto a agregar:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        Producto nuevoProducto = new Producto(0, nombre, precio, descripcion, stock);
        productoDAO.crear(nuevoProducto);
        System.out.println("Producto agregado con éxito.");
    }

    private static void modificarProducto() throws SQLException {
        System.out.print("\nIngrese el ID del producto a modificar: ");
        int idProducto = scanner.nextInt();
        scanner.nextLine();

        Producto producto = productoDAO.leer(idProducto);
        if (producto != null) {
            System.out.print("Nuevo nombre (" + producto.getNombre() + "): ");
            String nuevoNombre = scanner.nextLine();
            producto.setNombre(!nuevoNombre.isEmpty() ? nuevoNombre : producto.getNombre());

            System.out.print("Nuevo precio (" + producto.getPrecio() + "): ");
            double nuevoPrecio = scanner.nextDouble();
            scanner.nextLine();
            producto.setPrecio(nuevoPrecio > 0 ? nuevoPrecio : producto.getPrecio());

            System.out.print("Nueva descripción (" + producto.getDescripcion() + "): ");
            String nuevaDescripcion = scanner.nextLine();
            producto.setDescripcion(!nuevaDescripcion.isEmpty() ? nuevaDescripcion : producto.getDescripcion());

            System.out.print("Nuevo stock (" + producto.getCantidad() + "): ");
            int nuevoStock = scanner.nextInt();
            scanner.nextLine();
            producto.setCantidad(nuevoStock > 0 ? nuevoStock : producto.getCantidad());

            productoDAO.actualizar(producto);
            System.out.println("Producto modificado con éxito.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void eliminarProducto() throws SQLException {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int idProducto = scanner.nextInt();
        scanner.nextLine();

        Producto producto = productoDAO.leer(idProducto);
        if (producto != null) {
            productoDAO.eliminar(idProducto);
            System.out.println("Producto eliminado con éxito.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void gestionarCategorias() throws SQLException {
        System.out.println("\n--- Gestión de Categorías ---");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Agregar Categoría");
        System.out.println("2. Modificar Categoría");
        System.out.println("3. Eliminar Categoría");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 ->
                agregarCategoria();
            case 2 ->
                modificarCategoria();
            case 3 ->
                eliminarCategoria();
            default ->
                System.out.println("Opción no válida.");
        }
    }

    private static void agregarCategoria() throws SQLException {
        System.out.print("Ingrese el nombre de la nueva categoría: ");
        String nombre = scanner.nextLine();

        Categoria nuevaCategoria = new Categoria(0, nombre);
        categoriaDAO.crear(nuevaCategoria);
        System.out.println("Categoría agregada con éxito.");
    }

    private static void modificarCategoria() throws SQLException {
        System.out.print("Ingrese el ID de la categoría a modificar: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();

        Categoria categoria = categoriaDAO.leer(idCategoria);
        if (categoria != null) {
            System.out.print("Nuevo nombre (" + categoria.getNombre() + "): ");
            String nuevoNombre = scanner.nextLine();
            categoria.setNombre(!nuevoNombre.isEmpty() ? nuevoNombre : categoria.getNombre());

            categoriaDAO.actualizar(categoria);
            System.out.println("Categoría modificada con éxito.");
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }

    private static void eliminarCategoria() throws SQLException {

        System.out.print("Ingrese el ID de la categoría a eliminar: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();

        Categoria categoria = categoriaDAO.leer(idCategoria);
        if (categoria != null) {
            categoriaDAO.eliminar(idCategoria);
            System.out.println("Categoría eliminada con éxito.");
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }

    private static void verCarrito() {
        System.out.println("\n--- Carrito de Compras ---");
        carritoCompra.mostrarProductosDelCarrito();
        System.out.println("Total: " + carritoCompra.calcularTotal());
    }

    private static void realizarCompra() throws SQLException {
        if (usuarioActual == null || usuarioActual.getId_usuario() == 0) {
            System.out.println("Error: Debes iniciar sesión o registrar un usuario antes de realizar una compra.");
            return;
        }
        if (!esAdmin) {
            System.out.println("\n--- Realizar Compra ---");

            List<Producto> productos = productoDAO.leerTodos();

            if (productos.isEmpty()) {
                System.out.println("No hay productos disponibles.");
                return;
            }

            System.out.println("\n--- Productos Disponibles ---");
            for (Producto producto : productos) {
                System.out.printf("ID: %d - %s - %.2f - %s (Stock: %d)%n",
                        producto.getId_producto(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getDescripcion(),
                        producto.getCantidad());
            }

            boolean continuar = true;
            while (continuar) {
                System.out.print("Ingrese el ID del producto que desea agregar al carrito (o 0 para finalizar): ");
                int idProducto = scanner.nextInt();
                scanner.nextLine();

                if (idProducto == 0) {
                    continuar = false; // Salir del bucle si el usuario no quiere agregar más productos
                } else {
                    Producto productoSeleccionado = productoDAO.leer(idProducto);
                    if (productoSeleccionado != null) {
                        System.out.print("Ingrese la cantidad que desea comprar: ");
                        int cantidad = scanner.nextInt();
                        scanner.nextLine();

                        if (cantidad > 0 && cantidad <= productoSeleccionado.getCantidad()) {
                            carritoCompra.agregarAlCarrito(productoSeleccionado, cantidad);
                            System.out.println("Producto agregado al carrito.");
                        } else {
                            System.out.println("Cantidad inválida. Intente de nuevo.");
                        }
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                }
            }

            verCarrito();

            if (carritoCompra.calcularTotal() > 0) {
                System.out.println("Procesando pago...");
                Pago pago = new Pago(0, carritoCompra.calcularTotal());
                if (!pago.verificarPago()) {
                    System.out.println("El monto del pago debe ser mayor a cero.");
                    return;
                }

                String direccionEnvio = usuarioActual.getDireccion();
                Envio envio = new Envio(0, "En proceso", LocalDate.now(), direccionEnvio);
                EnvioDAO envioDAO = new EnvioDAO();
                envioDAO.crear(envio);

                double total = carritoCompra.calcularTotal();
                Pedido pedido = new Pedido(usuarioActual, "Pendiente", new Date(), total);

                // Guardar el pedido en la base de datos
                PedidoDAO pedidoDAO = new PedidoDAO();
                pedidoDAO.crear(pedido);

                // Actualizar stock del producto
                for (CarritoItem item : carritoCompra.getItems()) {
                    Producto producto = item.getProducto();
                    producto.setCantidad(producto.getCantidad() - item.getCantidad());
                    productoDAO.actualizar(producto);
                }

                // Generar el voucher
                generarVoucher(pedido, carritoCompra.getItems(), direccionEnvio);

                // Vaciar el carrito después de la compra
                carritoCompra.vaciarCarrito();

                System.out.println("Compra realizada exitosamente.");
            } else {
                System.out.println("El carrito está vacío. No se puede realizar la compra.");
            }
        } else {
            System.out.println("Los administradores no pueden realizar compras.");
        }
    }

    private static void generarVoucher(Pedido pedido, List<CarritoItem> items, String direccionEnvio) {
        System.out.println("\n--- Voucher de Compra ---");
        System.out.println("Número de Pedido: " + pedido.getId_Pedido());
        System.out.println("Fecha: " + pedido.getFecha_Pedido());
        System.out.println("Usuario: " + usuarioActual.getNombre());
        System.out.println("Dirección de Envío: " + direccionEnvio);
        System.out.println("\n--- Detalles de la Compra ---");

        for (CarritoItem item : items) {
            System.out.printf("%s x%d - Subtotal: %.2f%n",
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    item.calcularSubtotal());
        }

        System.out.printf("\nTotal a pagar: %.2f%n", pedido.getTotal());
        System.out.println("--------------------------");
    }

    public Producto buscarProducto(int idProducto) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            return productoDAO.leer(idProducto);
        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }

    private static void generarInforme() throws SQLException {
        System.out.println("\n--- Generar Informe de Ventas ---");
        InformeDAO informeDAO = new InformeDAO();
        informeDAO.generarInforme();

    }

    private static void gestionarUsuarios() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Gestión de Usuarios ===");
        System.out.println("1. Agregar Usuario");
        System.out.println("2. Modificar Usuario");
        System.out.println("3. Eliminar Usuario");
        System.out.print("Seleccione una opción: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1 ->
                agregarUsuario();
            case 2 ->
                modificarUsuario();
            case 3 ->
                eliminarUsuario();
            default ->
                System.out.println("Opción inválida.");
        }
    }

    private static void agregarUsuario() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean agregado = false;

        while (!agregado) {
            try {
                System.out.print("Ingrese el nombre del usuario: ");
                String nombre = scanner.nextLine();

                System.out.print("Ingrese el correo electrónico: ");
                String email = scanner.nextLine();

                System.out.print("Ingrese la contraseña: ");
                String contraseña = scanner.nextLine();

                String tipo = "Cliente";

                System.out.println("Ingrese la direccion del usuario: ");
                String direccion = scanner.nextLine();

                System.out.println("Ingrese el telefono del usuario: ");
                int telefono = scanner.nextInt();

                Usuario usuario = new Usuario(0, nombre, email, contraseña, tipo, direccion, telefono);
                usuarioDAO.crear(usuario);

                System.out.println("Usuario agregado con éxito.");
                agregado = true;
            } catch (SQLException e) {
                System.err.println("Error al agregar el usuario: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, ingrese los datos nuevamente.");
                scanner.nextLine(); // Limpiar el buffer del scanner
            }
        }
    }

    private static void modificarUsuario() throws SQLException {

        /// Obtener todos los usuarios
        List<Usuario> usuarios = usuarioDAO.leerTodos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios disponibles.");
            return;
        }

        // Mostrar la lista de usuarios
        System.out.println("Usuarios disponibles:");
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId_usuario() + " - Nombre: " + usuario.getNombre()
                    + " - Correo: " + usuario.getEmail() + " - Teléfono: " + usuario.getTelefono());
        }

        System.out.print("Ingrese el ID del usuario a modificar: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = usuarioDAO.leer(idUsuario); // Leer el usuario existente
        if (usuario != null) {
            System.out.println("Datos actuales del usuario: ");
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Contraseña: " + usuario.getContraseña());
            System.out.println("Dirección: " + usuario.getDireccion());
            System.out.println("Teléfono: " + usuario.getTelefono());

            System.out.print("Nuevo nombre (" + usuario.getNombre() + "): ");
            String nuevoNombre = scanner.nextLine();
            System.out.print("Nuevo email (" + usuario.getEmail() + "): ");
            String nuevoEmail = scanner.nextLine();
            System.out.print("Nueva contraseña (" + usuario.getContraseña() + "): ");
            String nuevaContraseña = scanner.nextLine();
            System.out.print("Nueva dirección (" + usuario.getDireccion() + "): ");
            String nuevaDireccion = scanner.nextLine();
            System.out.print("Nuevo teléfono (" + usuario.getTelefono() + "): ");
            int nuevoTelefono = scanner.hasNextInt() ? scanner.nextInt() : usuario.getTelefono();
            scanner.nextLine(); // Limpiar el buffer del scanner

            // Actualizar los datos del usuario
            usuario.setNombre(!nuevoNombre.isEmpty() ? nuevoNombre : usuario.getNombre());
            usuario.setEmail(!nuevoEmail.isEmpty() ? nuevoEmail : usuario.getEmail());
            usuario.setContraseña(!nuevaContraseña.isEmpty() ? nuevaContraseña : usuario.getContraseña());
            usuario.setDireccion(!nuevaDireccion.isEmpty() ? nuevaDireccion : usuario.getDireccion());
            usuario.setTelefono(nuevoTelefono);

            usuarioDAO.actualizar(usuario); // Actualizar en la base de datos
            System.out.println("Usuario modificado con éxito.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private static void eliminarUsuario() throws SQLException {

        /// Obtener todos los usuarios
        List<Usuario> usuarios = usuarioDAO.leerTodos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios disponibles.");
            return;
        }

        // Mostrar la lista de usuarios
        System.out.println("Usuarios disponibles:");
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId_usuario() + " - Nombre: " + usuario.getNombre()
                    + " - Correo: " + usuario.getEmail() + " - Teléfono: " + usuario.getTelefono());
        }

        System.out.print("Ingrese el ID del usuario a eliminar: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = usuarioDAO.leer(idUsuario); // Verificar si el usuario existe
        if (usuario != null) {
            System.out.println("¿Está seguro de que desea eliminar al usuario " + usuario.getNombre() + "? (s/n)");
            String confirmacion = scanner.nextLine();
            if (confirmacion.equalsIgnoreCase("s")) {
                usuarioDAO.eliminar(idUsuario); // Eliminar de la base de datos
                System.out.println("Usuario eliminado con éxito.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
}
