# Hatás-táblázat

A táblázat sorai a módosított funkciókat, az oszlopok a potenciálisan érintett funkciókat mutatják.

| Módosítva \ Érintett  | ComponentList | FurnitureBody  | FrontElement   | RawMaterial    | RawMaterialType |
|----------------------:|:-------------:|:--------------:|:--------------:|:--------------:|:---------------:|
| ComponentList         | High          | High           | Medium         | High           | Medium          |
| FurnitureBody         | High          | High           | High           | Medium         | Low             |
| FrontElement          | Medium        | High           | High           | Medium         | Medium          |
| RawMaterial           | Medium        | Medium         | Medium         | High           | High            |
| RawMaterialType       | Low           | Low            | Low            | High           | High            |

**Jelmagyarázat**

- **High:** Közvetlen, jelentős hatás — API/mezők vagy viselkedés módosulása valószínű.
- **Medium:** Közepes hatás — adat- vagy viselkedésfüggések miatt módosulhatnak kapcsolódó funkciók.
- **Low:** Kis vagy indirekt hatás — ritkán szükséges módosítás, általában csak integrációs/viselkedési következmény.
