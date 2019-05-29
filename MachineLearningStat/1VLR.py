import numpy as np
n = 0
x = np.zeros(1)
y = np.zeros(1)
p = np.zeros(2)
ls = 0


def init(a, b, lr, n1):
    global n
    n = n1
    global x
    x = np.zeros(n)
    global y
    y = np.zeros(n)

    it = np.nditer(x, op_flags=['readwrite'], flags=['f_index'])
    for x2 in it:
        x[it.index] = it.index
        y[it.index] = it.index

    p[0] = a
    p[1] = b
    global ls
    ls = lr


def debug():
    print(x)
    print(y)
    print(p)
    print(cost())


def h():
    return (p[1] * x) + p[0]


def cost():
    return .5 * np.sum(np.power((h() - y), 2))


def learn(epochs):
    for i in range(0, epochs):
        p0 = (sum(h() - y) / n) * ls
        p1 = (sum((h() - y) * x) / n) * ls
        p[0] -= p0
        p[1] -= p1
        print(str(i) + ' ' + str(cost()))


init(4, 5, .00001, 500)
debug()
learn(1000000)

