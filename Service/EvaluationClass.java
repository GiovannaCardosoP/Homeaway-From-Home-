package Service;

public class EvaluationClass implements Evaluation {
    private static final long serialVersionUID = 0L;
    private String comment;
    private int rank;

    public EvaluationClass(String c, int r) {
        comment = c;
        rank = r;
    }

    @Override
    public int getRank() {
        return rank;
    }


    // confirmar que é isto que a prof quer
    @Override
    public boolean hasTag(String tag) {
        String c = comment;

        tag = " " + tag + " ";
        c = " " + c + " ";

        if(comment == null) return false;

        char[] text = c.toUpperCase().toCharArray();
        char[] study = tag.toCharArray();

        int n = text.length;
        int m = study.length;

        int[] index = new int[m];
        buildTable(study, index);

        int j = 0;
        int k = 0;

        while (j < n) {
            if (text[j] == study[k]) {
                if (k == m - 1) return true;
                k++;
                j++;

            } else  if (k > 0){
                k = index[k - 1];

            }else {
                j++;
            }
        }
        return false;
    }



    private void buildTable(char[] study,int[] index){
        int size = study.length;

        int i = 1;
        int j = 0;

        while(i < size){
            if (study[j] == study[i]) {
                i++;
                j++;
            } else if (j > 0) {
                j = index[j-1];
            }else{
                j++;
            }
        }
    }
}



