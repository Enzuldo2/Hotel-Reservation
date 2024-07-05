### Diagrama de classes
```plantuml
@startuml
class Hotel {

}

class Room {
    +idRoom: int
    +price: float
    +capacity: int
    +is_active: bool
}

abstract class User {
    +idUser: int 
    +username: str
    +password: str
    +is_superuser: bool 
    +is_active: bool 

    +createUser(): int
    +getUser(): User
    +updateUser(): bool
    +deleteUser(): bool
}

class Cliente {
    +clienteID: int
    +viewProducts() : void
    +makePurchase() : void
}

class Admin {
    +adminID: int
    +addProduct() : void
    +removeProduct() : void
    +viewSalesReports() : void
}

User <|-- Cliente
User <|-- Admin

@enduml
```
