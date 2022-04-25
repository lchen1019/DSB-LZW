#include "File.h"
#include <ctime>

File::File(const char* read_file, const char* write_file) {
    // this->p = new char[N];
    this->w = new char[N];
    this->pos = 0, this->pos_w = 0;
    this->half_w = false;
    this->half_p = false;
    this->fp_read = fopen(read_file, "rb");
    printf("%d\n", fp_read);
    this->fp_write = fopen(write_file, "wb");
    this->size = fread(this->p, 1, N, fp_read);
}

ll File::get_8() {
    if (pos >= size) {
        this->size = fread(this->p, 1, N, this->fp_read);
        this->pos = 0;
        if (this->size == 0) {
            return -1L;
        }
    }
    char c = this->p[this->pos++];
    ll res = c & 0x7f;
    if (c & 0x80)
        res += 128L;
    // printf("res = %d\n",res);
    return res;
}

// int main() {
//      char file_read[] = "test.pdf";
//     char file_write[] = "test.cl";
//     File file(file_read,file_write);
//     while(file.get_8() != -1L);
// }

void File::write_12(int code) {
    // printf("code = %d,pos = %d\n",code,pos_w);
    if (this->pos_w == N) {
        fwrite(w, 1, N, fp_write);
        this->pos_w = 0;
        this->half_w = true;
        int high_8 = (code >> 4) & 0x000000ff;
        w[pos_w++] = high_8;
        int low_4 = (code << 4) & 0x000000f0;
        w[pos_w] = low_4;
    } else {
        if (half_w) {
            int high_4 = (code >> 8) & 0x0000000f;
            char temp = high_4;
            w[pos_w] = w[pos_w] | temp;
            pos_w++;
            int low_8 = code & 0x000000ff;
            w[pos_w++] = low_8;
            half_w = false;
            // printf("%d  %d\n",high_4,low_8);
        } else {
            this->half_w = true;
            int high_8 = (code >> 4) & 0x000000ff;
            w[pos_w++] = high_8;
            int low_4 = (code << 4) & 0x000000f0;
            w[pos_w] = low_4;
            if(high_8 > 15){

            // printf("asdasd  %d  %d\n",high_8,low_4);
            }
        }
    }
}

void File::close() {
    if (half_w){
        fwrite(w, 1, pos_w + 1, fp_write);
    } else {
        fwrite(w, 1, pos_w, fp_write);
    }
    fclose(fp_read);
    fclose(fp_write);
}

int File::get_12() {
    if (pos >= size || pos == size - 1 && half_p) {
        this->size = fread(this->p, 1, N, this->fp_read);
        this->pos = 0;
        if (this->size == 0) {
            return -1L;
        }
    }
    int res;
    if (!half_p) {
        char c = this->p[this->pos++];
        char d = this->p[this->pos];
        res = ((0x000000ff & c) << 4) | ((0x000000ff & d) >> 4);
    } else {
        char c = this->p[this->pos++];
        char d = this->p[this->pos++];
        res = ((0x0000000f & c) << 8) | (0x000000ff & d);
    }
    half_p = !half_p;
    return res;
}

void File::write_8(P code) {
    ll cur = code.first;
    short tot = code.second;
    // printf("code.first  = %lld,code.second = %d\n",cur,(int)tot);
    while (tot--) {
        char c = (cur >> 8 * tot) & 0x00000000000000ff;
        // printf("%d   w  %d\n",(int) ++cnt,0x000000ff & c);
        w[pos_w++] = c;
        if(pos_w == N) {
            fwrite(w,1,N,fp_write);
            pos_w = 0;
        }
    }
}

// int main() {
//     int time1 = clock();
//     char p[] = "D:\\cpp_code\\winter_study_2022\\test.zip";
//     char q[] = "D:\\cpp_code\\winter_study_2022\\test123.pdf";
//     File file(p,q);
//     int a;
//     while((a = file.get_8()) !=-1L);
//     int time2 = clock();
//     int ans = time2 - time1;
//     printf("%d\n",ans);
// }