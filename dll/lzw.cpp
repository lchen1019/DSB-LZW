#include <string>
#include <vector>
#include "File.cpp"
#include "HashChains.cpp"
using namespace std;
typedef long long ll;
typedef pair<ll, short> P;

const int split_length = 8;
const int code_length = 12;
const int max_size = 1 << code_length;
const int type_length = 8;
int dictionary_init_size;
int dictionary_pos;

// 函数声明
extern void init(int size);
extern void compress();

void compress(const char* file_read, const char* file_write) {
    HashChains* init_dictionary = new HashChains();
    HashChains* dictionary = new HashChains();
    dictionary_pos = dictionary_init_size;
    for (int i = 0; i < dictionary_init_size; i++) {
        dictionary->add(make_pair(i, 1), i);
    }
    memcpy(init_dictionary->head, dictionary->head, 3508744);
    File file(file_read, file_write);
    ll cur = file.get_8();
    ll pre = cur;
    ll next = -1L;
    int tot = 0;
    int tot_ = 1;
    while (true) {
        short cnt_left = 1;
        while (true) {
            pre = cur;
            next = file.get_8();
            tot_++;
            if (next != -1L) {
                cur = (cur << split_length) + next;
                cnt_left++;
            } else {
                break;
            }
            if (cnt_left >= type_length ||
                !dictionary->find(make_pair(cur, cnt_left)))
                break;
        }
        cur = pre;
        if (next == -1L) {
            tot++;
            file.write_12(dictionary->get(make_pair(cur, cnt_left)));
            file.close();
            break;
        }
        file.write_12(dictionary->get(make_pair(cur, cnt_left - 1)));
        tot++;
        cur = (cur << type_length) + next;
        if (cnt_left < type_length) {
            dictionary->add(make_pair(cur, cnt_left), dictionary_pos++);
        }
        cur = next;
        // printf("cur = %d\n",cur);
        if (dictionary_pos == max_size) {
            // for (int i = 0; i < 257; i++) {
            //     dictionary->head[i].dfs(dictionary->head[i].root);
            //     // init_dictionary->head[i].dfs(init_dictionary->head[i].root);
            // }
            dictionary_pos = dictionary_init_size;
            // printf("asdasd\n");
            // printf("head = %d\n",init_dictionary->head);
            // memcpy(dictionary->head, init_dictionary->head, 3508744);
            // printf("asd  %d\n",dictionary->head);
            // printf("asd  %d\n",init_dictionary->head);
            // printf("asdasd\n");
            // for (int i = 0; i < 257; i++) {
            //     dictionary->head[i].dfs(dictionary->head[i].root);
            //     // init_dictionary->head[i].dfs(init_dictionary->head[i].root);
            // }
            delete dictionary;
            dictionary = new HashChains;
            for (int i = 0; i < dictionary_init_size; i++) {
                dictionary->add(make_pair(i, 1), i);
            }
        }
    }
    file.close();
}

void unzip(const char* file_read, const char* file_write) {
    vector<P> dictionary(max_size);
    for (int i = 0; i < dictionary_init_size; i++) {
        dictionary[i] = make_pair(i, 1);
    }
    dictionary_pos = dictionary_init_size;
    File file(file_read, file_write);
    P pre = make_pair(-1, -1), cur;
    int key;
    int tot = 0, tot_ = 0;
    while ((key = file.get_12()) != -1L) {
        tot++;
        if (dictionary_pos == key) {
            if (pre.second < type_length - 1) {
                ll temp = pre.first >> (8 * (pre.second - 1));
                pre.first = (pre.first << 8) | temp;
                pre.second++;
                dictionary[dictionary_pos++] = pre;
            }
            cur = pre;
        } else if (dictionary_pos > key) {
            cur = dictionary[key];
            if (pre.first != -1 && pre.second <= type_length - 2) {
                ll temp = cur.first >> (8 * (cur.second - 1));
                ll next = (pre.first << 8) | temp;
                dictionary[dictionary_pos++] = make_pair(next, ++pre.second);
            }
        }
        pre = cur;
        file.write_8(cur);
        if (dictionary_pos == max_size - 1 && cur.second != 7) {
            tot_++;
            tot = 0;
            pre = make_pair(-1, -1);
            dictionary.resize(dictionary_init_size);
            dictionary_pos = dictionary_init_size;
        }
    }
    file.close();
}

int main() {
    char file_read[] = "D:\\cpp_code\\winter_study_2022\\test.pdf";
    char file_write[] = "test.cl";

    char file_read2[] = "D:\\cpp_code\\winter_study_2022\\test.cl";
    char file_write2[] = "ans.png";

    int time1 = clock();
    dictionary_init_size = 256;
    compress(file_read, file_write);
    int time2 = clock();
    int ans = time2 - time1;
    printf("ans1  %d\n", ans);
    unzip(file_read2, file_write2);
    int time3 = clock();
    int ans2 = time3 - time2;
    printf("ans2  %d  ", ans2);
}
