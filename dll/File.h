#include<fstream>
#include<cstring>
#include<cstdio>
#include<vector>
using namespace std;
typedef long long ll;
typedef pair<ll,short> P;

class File {
public:
    File(const char *read_file, const char *write_file);
    ll get_8();
    int get_12();
    void write_12(int code);
    void write_8(P code);
    void close();
private:
    const int N = 2049;
    FILE *fp_read,*fp_write;
    char *w;
    char p[2049];
    int pos,pos_w;
    int size;
    int cnt = 0;
    bool half_w, half_p;
};