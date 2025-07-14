# Class Diagram - ScoreRepository Pattern

```mermaid
classDiagram
    %% Interfaces
    class Repository~T~ {
        <<interface>>
        +save(data: T) boolean
        +load() T
        +deleteAllFiles() boolean
    }
    
    class FileManager {
        <<interface>>
        +getDataFile() Path
        +createBackup() void
        +restoreFromBackup() boolean
        +deleteAll() boolean
    }
    
    class Entry {
        <<interface>>
        +name() String
        +score() int
    }
    
    class Leaderboard {
        <<interface>>
        +getTopScores() List~Entry~
        +getTopScore() int
        +addEntry(entry: Entry) boolean
        +save() boolean
    }
    
    %% Concrete Classes
    class ScoreRepository {
        -FOLDER: String = ".coffeBreak"
        -FILE_NAME: String = "dk_leaderboard.ser"
        -fileManager: FileManager
        +ScoreRepository()
        +save(data: List~Entry~) boolean
        +load() List~Entry~
        +deleteAllFiles() boolean
    }
    
    class ScoreFileManager {
        -dataFile: Path
        -backupFile: Path
        -dataDir: Path
        +ScoreFileManager(folderName: String, fileName: String)
        +getDataFile() Path
        +createBackup() void
        +restoreFromBackup() boolean
        +deleteAll() boolean
    }
    
    class GameLeaderboard {
        +MAX_ENTRIES: int = 5
        -repository: Repository~List~Entry~~
        -entries: List~Entry~
        +GameLeaderboard()
        +getTopScores() List~Entry~
        +getTopScore() int
        +addEntry(entry: Entry) boolean
        +save() boolean
    }
    
    class ScoreEntry {
        -name: String
        -score: int
        +ScoreEntry(name: String, score: int)
        +name() String
        +score() int
        +toString() String
    }
    
    class RepositoryException {
        <<exception>>
        +RepositoryException(message: String)
        +RepositoryException(message: String, cause: Throwable)
    }
    
    %% Implementation relationships
    ScoreRepository ..|> Repository~List~Entry~~ : implements
    ScoreFileManager ..|> FileManager : implements
    GameLeaderboard ..|> Leaderboard : implements
    ScoreEntry ..|> Entry : implements
    
    %% Composition relationships
    ScoreRepository *-- FileManager : uses
    GameLeaderboard *-- Repository~List~Entry~~ : uses
    
    %% Dependency relationships
    ScoreRepository ..> Entry : persists
    ScoreRepository ..> RepositoryException : throws
    GameLeaderboard ..> Entry : manages
    
    %% Nested class
    ScoreRepository +-- RepositoryException : nested
    
    %% Association with generic types
    Repository~List~Entry~~ --> Entry : stores list of
    
    %% Notes
    note for ScoreRepository "Implements Repository Pattern\nwith automatic backup/recovery"
    note for ScoreFileManager "Handles low-level file operations\nwith backup mechanisms"
    note for RepositoryException "Custom exception for\nrepository-specific errors"
```

## Descrizione del Pattern

### 🎯 **Repository Pattern Implementato:**

#### **Interfacce:**
- **`Repository<T>`**: Interfaccia generica per persistenza
- **`FileManager`**: Astrazione per operazioni sui file
- **`Entry`**: Rappresenta una voce della leaderboard
- **`Leaderboard`**: Interfaccia per gestione classifiche

#### **Implementazioni Concrete:**
- **`ScoreRepository`**: Repository specializzato per Entry
- **`ScoreFileManager`**: Gestore file concreto con backup
- **`GameLeaderboard`**: Implementazione leaderboard con capacità limitata
- **`ScoreEntry`**: Record immutabile per name/score

#### **Relazioni Chiave:**

1. **Implementazione (`...|>`):**
   - `ScoreRepository` implementa `Repository<List<Entry>>`
   - `ScoreFileManager` implementa `FileManager`

2. **Composizione (`*--`):**
   - `ScoreRepository` contiene `FileManager`
   - `GameLeaderboard` contiene `Repository<List<Entry>>`

3. **Dipendenza (`...>`):**
   - `ScoreRepository` usa `Entry` per la persistenza
   - `ScoreRepository` può lanciare `RepositoryException`

4. **Classe Nested (`+--`):**
   - `RepositoryException` è nested in `ScoreRepository`

### 🔧 **Benefici del Design:**

- **Separazione delle responsabilità**: Repository vs FileManager
- **Robustezza**: Backup automatici e recovery
- **Testabilità**: Interfacce mockabili
- **Estendibilità**: Facile aggiungere nuovi tipi di repository
- **Type Safety**: Generics per type safety
