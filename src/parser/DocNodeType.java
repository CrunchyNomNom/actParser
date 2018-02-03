package parser;

public enum DocNodeType {
    ROOT,
    DZIAL,
    ROZDZIAL,
    TYTUL,
    ARTYKUL,
    USTEP,
    PUNKT,
    LITERA,
    UNDEFINED;

    public int getPriority(){
        switch (this){
            case ROOT:      return 8;
            case DZIAL:     return 7;
            case ROZDZIAL:  return 6;
            case TYTUL:     return 5;
            case ARTYKUL:   return 4;
            case USTEP:     return 3;
            case PUNKT:     return 2;
            case LITERA:    return 1;
            case UNDEFINED: return 0;
            default:        return -1;
        }
    }

    public DocNodeType refactor(String s) {
        switch (s){
            case "d":      return DZIAL;
            case "r":      return ROZDZIAL;
            case "a":      return ARTYKUL;
            case "u":      return USTEP;
            case "p":      return PUNKT;
            case "l":      return LITERA;
            default:       return UNDEFINED;
        }
    }
}
