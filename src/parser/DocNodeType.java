package parser;

public enum DocNodeType {
    ROOT,
    DZIAL,
    ROZDZIAL,
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
            case ARTYKUL:   return 4;
            case USTEP:     return 3;
            case PUNKT:     return 2;
            case LITERA:    return 1;
            case UNDEFINED: return 0;
            default:        return -1;
        }
    }

}
