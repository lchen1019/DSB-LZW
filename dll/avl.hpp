#include <cstdio>
#include<string>
#include<cstring>
#include <iostream>
#include <queue>
#include "space.hpp"
#define max(a, b) (((a) > (b)) ? (a) : (b))
#define NotExist -1  // 未找到异常
using namespace std;

template <class T1, class T2>
class Node {
   public:
    Node();
    // 下面三个未实现
    // Node(const Node<T1,T2>& node);
    // ~Node();
    // Node<T1,T2>& operator=(const Node<T1,T2>& node);
    T1 key;
    T2 value;
    Node<T1, T2>*left, *parent, *right;
    template <class T3, class T4>
    friend class Avl;

   private:
    int height;
};

template <class T1, class T2>
class Avl {
   public:
    Avl();
    // 下面三个未实现
    // Avl(const Avl<T1,T2>& avl);
    // ~Avl();
    // Avl<T1,T2>& operator=(const Avl<T1,T2>& avl);
    void insert(T1 key, T2 value);
    bool find(T1 key);
    T2 get(T1 key);
    void dfs(Node<T1, T2>* pos);
    Node<T1, T2>* root;
    // Space<Node<T1,T2> > *space;  // 设置Avl树的最大高度,使用模拟指针管理内存
    Node<T1,T2> space[15];
    Node<T1,T2>* get();
   private:
    Node<T1, T2>& find_pre(T1 key);
    int pos;
    const int max_size = 5;
};

template <class T1, class T2>
Node<T1,T2>* Avl<T1,T2>::get() {
    return &space[++pos];
}

template <class T1, class T2>
Node<T1, T2>::Node() {
    this->height = 1;
    this->left = NULL;
    this->right = NULL;
    this->parent = NULL;
}

template <class T1, class T2>
Avl<T1, T2>::Avl() {
    root = NULL;
    pos = -1;
}

template <class T1, class T2>
Node<T1, T2>& Avl<T1, T2>::find_pre(T1 key) {
    Node<T1, T2>*cur = this->root, *pre = NULL;
    while (cur) {
        if (cur->key < key) {
            pre = cur;
            cur = cur->right;
        } else if (cur->key > key) {
            pre = cur;
            cur = cur->left;
        } else {
            return *pre;
        }
    }
    return *pre;
}

template <class T1, class T2>
bool Avl<T1, T2>::find(T1 key) {
    if (!root)
        return false;
    if (root->key == key)
        return true;
    Node<T1, T2>* pos = &find_pre(key);
    return (pos->left && pos->left->key == key) ||
           (pos->right && pos->right->key == key);
}

template <class T1, class T2>
void Avl<T1, T2>::insert(T1 key, T2 value) {
    // printf("================Add %d==================\n", key);
    // 找到插入在哪
    Node<T1, T2>* pos = &find_pre(key);
    if (root == NULL) {
        root = get();
        // root = new Node<T1, T2>;
        root->key = key;
        root->value = value;
        return;
    }
    if (root->key == key)
        return;
    if ((pos->left && pos->left->key == key) ||
        (pos->right && pos->right->key == key))
        return;
    // 左 or 右
    // Node<T1, T2>* add = new Node<T1, T2>;
    Node<T1, T2>* add = get();
    add->key = key;
    add->value = value;
    bool add_in_left = false;
    bool need_update = pos->left || pos->right;
    if (pos->key < key) {
        pos->right = add;
        add->parent = pos;
        pos = pos->right;
    } else {
        pos->left = add;
        add->parent = pos;
        pos = pos->left;
        add_in_left = true;
    }
    Node<T1, T2>* cur = pos;
    while (cur) {
        int left_height = cur->left ? cur->left->height : 0;
        int right_height = cur->right ? cur->right->height : 0;
        int bf = left_height - right_height;
        cur->height = max(left_height, right_height) + 1;
        if (bf == 2 || bf == -2)
            break;
        cur = cur->parent;
    }
    if (!cur)
        return;
    if (cur->key < key) {
        if (cur->right->key < key) {
            // printf("RR\n");
            // 1.RR
            Node<T1, T2>* cur_right = cur->right;
            Node<T1, T2>* p = cur->parent;
            Node<T1, T2>* cur_rl = cur_right->left;
            Node<T1, T2>* cur_rr = cur->right->right;
            if (p) {
                if (p->key < cur_right->key) {
                    p->right = cur_right;
                } else {
                    p->left = cur_right;
                }
            } else {
                root = cur_right;
            }
            cur_right->left = cur;
            cur->right = cur_rl;
            cur_right->parent = p;
            cur->parent = cur_right;
            if (cur_rl)
                cur_rl->parent = cur;
            // 更新
            cur->height -= 2;
        } else {
            // 2.RL
            // printf("RL\n");
            Node<T1, T2>* p = cur->parent;
            Node<T1, T2>* cur_right = cur->right;
            Node<T1, T2>* cur_rl = cur_right->left;
            Node<T1, T2>* cur_rll = cur_rl->left;
            Node<T1, T2>* cur_rlr = cur_rl->right;
            if (p) {
                if (p->key < cur_rl->key) {
                    p->right = cur_rl;
                } else {
                    p->left = cur_rl;
                }
            } else {
                root = cur_rl;
            }
            cur_rl->left = cur;
            cur_rl->right = cur_right;
            cur->right = cur_rll;
            cur_right->left = cur_rlr;
            cur_rl->parent = p;
            cur->parent = cur_rl;
            cur_right->parent = cur_rl;
            if (cur_rll)
                cur_rll->parent = cur;
            if (cur_rlr)
                cur_rlr->parent = cur_right;
            // 更新
            cur->height -= 2;
            cur_right->height--;
            cur_rl->height++;
        }
    } else {
        if (cur->left->key > key) {
            // 3.LL
            // printf("LL\n");
            Node<T1, T2>* cur_left = cur->left;
            Node<T1, T2>* p = cur->parent;
            Node<T1, T2>* cur_ll = cur_left->left;
            Node<T1, T2>* cur_lr = cur_left->right;
            if (p) {
                if (p->key > cur_left->key) {
                    p->left = cur_left;
                } else {
                    p->right = cur_left;
                }
            } else {
                root = cur_left;
            }
            cur_left->right = cur;
            cur->left = cur_lr;
            cur_left->parent = p;
            cur->parent = cur_left;
            if (cur_lr)
                cur_lr->parent = cur;
            // 更新
            cur->height -= 2;
        } else {
            // 4.LR
            // printf("LR\n");
            Node<T1, T2>* p = cur->parent;
            Node<T1, T2>* cur_left = cur->left;
            Node<T1, T2>* cur_lr = cur_left->right;
            Node<T1, T2>* cur_lrr = cur_lr->right;
            Node<T1, T2>* cur_lrl = cur_lr->left;
            if (p) {
                if (p->key < cur_lr->key) {
                    p->right = cur_lr;
                } else {
                    p->left = cur_lr;
                }
            } else {
                root = cur_lr;
            }
            cur_lr->left = cur_left;
            cur_lr->right = cur;
            cur->left = cur_lrr;
            cur_left->right = cur_lrl;
            cur_lr->parent = p;
            cur->parent = cur_lr;
            cur_left->parent = cur_lr;
            if (cur_lrr)
                cur_lrr->parent = cur;
            if (cur_lrl)
                cur_lrl->parent = cur_left;
            // 更新
            cur->height -= 2;
            cur_left->height--;
            cur_lr->height++;
        }
    }
}

template <class T1, class T2>
T2 Avl<T1, T2>::get(T1 key) {
    if (root->key == key)
        return root->value;
    Node<T1, T2>* pos = &find_pre(key);
    if (pos->left && pos->left->key == key)
        return pos->left->value;
    if (pos->right && pos->right->key == key)
        return pos->right->value;
    printf("avl 未找到\n");
    // throw NotExist;
}

// 形成树结构
template <class T1, class T2>
void Avl<T1, T2>::dfs(Node<T1, T2>* pos) {
        cout << pos->key.first << "," << pos->key.second << endl;
    // queue<Node<T1, T2>*> q;
    // q.push(pos);
    // FILE *fp = fopen("g.dot","rw");
    // printf("graph {\n");
    // while (!q.empty()) {
    //     Node<T1, T2>* top = q.front();
    //     q.pop();
    //     if (!top)
    //         continue;
    //     q.push(top->left);
    //     q.push(top->right);
    //     if (top->left) {
    //         cout << (top->key.first,top->key.second) << "--" << (top->right->key.first,top->right->key.second) << endl;
    //     }
    //     if (top->right) {
    //         cout << (top->key.first,top->key.second) << "--" << (top->right->key.first,top->right->key.second) << endl;
    //     }
    // }
    // printf("}\n");
}
