# Wanted Gaming Classic Server

An English-language Lineage 1 server emulator based on [l1j-en/classic](https://github.com/l1j-en/classic), targeting the **Tikal and Antharas** client. Licensed under GNU GPL v2.

## Requirements

- **Client:** Lineage 1 Tikal and Antharas Update
- **Connector:** [Connector 2.40](https://github.com/l1j-en/classic/wiki) to connect to the server
- **Java:** JDK 11 LTS
- **Build Tool:** Apache Ant
- **Database:** MySQL or MariaDB

## Quick Start

### 1. Database Setup

Import the schema into your MySQL/MariaDB instance:

```sql
mysql -u root -p < db/l1jdb_m10.sql
```

Configure your database connection in `config/server.properties`.

### 2. Build

```bash
ant
```

This produces `l1jen.jar`.

### 3. Run

```bash
# Windows
serverstart-console.bat

# Linux
./serverstart.sh
```

The server listens on port **2000** by default.

### 4. Connect

Use **Connector 2.40** to point your Tikal and Antharas client to your server's IP address.

## Configuration

| File | Purpose |
|------|---------|
| `config/server.properties` | Server host/port, database, security |
| `config/rates.properties` | XP, drop, regen, enchant rates |
| `config/charsettings.properties` | Character creation and limits |
| `config/altsettings.properties` | Alternate gameplay rules |
| `config/pcommands.properties` | Player commands |

## License

GNU General Public License v2 — see [COPYING](COPYING).
