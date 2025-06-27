-- Crear base de datos
CREATE DATABASE TiendaVirtual;
GO

-- Usar la base de datos creada
USE TiendaVirtual;
GO

-- Crear la tabla Usuario
CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) DEFAULT 'cliente' CHECK (tipo IN ('cliente', 'administrador')),
    direccion VARCHAR(50),
    telefono NVARCHAR(9)
);

-- Crear tabla Producto
CREATE TABLE Producto (
    id_producto INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    descripcion VARCHAR(255),
    cantidad INT NOT NULL
);

-- Crear tabla CarritoCompra
CREATE TABLE CarritoCompra (
    ID_carrito INT PRIMARY KEY IDENTITY(1,1),
    total DECIMAL(10, 2) NOT NULL
);

-- Crear tabla Pedido
CREATE TABLE Pedido (
    id_pedido INT PRIMARY KEY IDENTITY(1,1),
    id_usuario INT NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_pedido DATE NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- Crear tabla Envio
CREATE TABLE Envio (
    id_envio INT PRIMARY KEY IDENTITY(1,1),
    estado VARCHAR(50),
    fechaEnvio DATE,
    direccionEnvio VARCHAR(255)
);

-- Crear tabla Pago
CREATE TABLE Pago (
    id_pago INT PRIMARY KEY IDENTITY(1,1),
    monto DECIMAL(10, 2) NOT NULL,
    fechaPago DATE NOT NULL
);

-- Crear tabla Informe
CREATE TABLE Informe (
    id_informe INT PRIMARY KEY IDENTITY(1,1),
    total_ganancias DECIMAL(10, 2) NOT NULL,
    igv DECIMAL(10, 2) NOT NULL,
    total_pedidos INT NOT NULL,
    fecha_generacion DATETIME DEFAULT GETDATE()
);

-- Crear tabla Categoria (sin IDENTITY, para poder insertar ID manual)
CREATE TABLE Categoria (
    id_categoria INT PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL
);

-- Insertar categorías manualmente, como las usas en tu DAO
INSERT INTO Categoria (id_categoria, nombre) VALUES
(1, 'Bebidas'),
(2, 'Snacks'),
(3, 'Limpieza'),
(4, 'Lácteos');

INSERT INTO Producto (id_producto, nombre, precio, descripcion, cantidad) VALUES
(1, 'Coca Cola 500ml', 3.50, 'Bebida gaseosa', 100),
(2, 'Inca Kola 1L', 5.00, 'Bebida gaseosa', 80),
(3, 'Agua Cielo 625ml', 2.00, 'Agua mineral', 200),
(4, 'Cerveza Pilsen 355ml', 4.50, 'Bebida alcohólica', 50),
(5, 'Pepsi 1L', 4.00, 'Bebida gaseosa', 90),
(6, 'Chips Ahoy', 4.50, 'Galletas con chips de chocolate', 150),
(7, 'Galletas Oreo', 2.50, 'Galletas de chocolate rellenas', 120),
(8, 'Papitas Lays', 3.00, 'Papas fritas', 200),
(9, 'Chocolatina Sublime', 1.80, 'Chocolatina con maní', 250),
(10, 'Chocman', 1.50, 'Pastelito cubierto de chocolate', 180),
(11, 'Leche Gloria 1L', 4.00, 'Leche evaporada', 300),
(12, 'Yogurt Gloria 1L', 5.50, 'Yogurt de fresa', 100),
(13, 'Queso Edam 250g', 10.00, 'Queso semi-duro', 60),
(14, 'Detergente Ariel 500g', 8.00, 'Detergente en polvo', 70),
(15, 'Jabón Ace 1L', 6.00, 'Jabón líquido', 80);


-- Verificar datos
SELECT * FROM Usuario;
SELECT * FROM Producto;
SELECT * FROM Categoria;
select * from Pedido;