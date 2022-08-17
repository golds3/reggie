package test;

import org.junit.jupiter.api.Test;

/**
 * @author 黄柏轩
 * @version 1.0
 */
public class test {
    int ans = 0;
    int mx,nx;
    int m=3,n=2,k=17;
    @Test
    public void movingCount() {
        if(k==0) return ;
        mx=m-1;
        nx=n-1;
        int[][] visited = new int[m][n];
        dfs(0,0,k,visited);
        return ;
    }
    void dfs(int m,int n,int k,int[][] visited){
        if(m<0||n<0||m>mx||n>nx||visited[m][n]==1) return;
        visited[m][n]=1;
        String s = String.valueOf(m)+n;
        int sum=0;
        for(int i = 0;i<s.length();i++){
            sum+= s.charAt(i)-'0';
        }
        if(sum>k) return;
        ans+=1;
        dfs(m+1,n,k,visited);
        dfs(m-1,n,k,visited);
        dfs(m,n-1,k,visited);
        dfs(m,n+1,k,visited);
        visited[m][n]=0;
    }
}
