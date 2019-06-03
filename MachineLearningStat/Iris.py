import numpy as np
import pandas as pd


x = np.zeros((150, 5))
y = np.zeros((150, 3))
p = np.random.random((3, 5))


def load_data():
    df = pd.read_csv('iris.csv')
    print(df)
    it = np.nditer(x, op_flags=['readwrite'], flags=['multi_index'])
    for x2 in it:
        x1 = it.multi_index[0]
        y1 = it.multi_index[1]
        ind = df.iloc[x1, y1]

        if ind == 'Versicolor':
            y[x1, 1] = 1
        elif ind == 'Virginica':
            y[x1, 2] = 1
        else:
             y[x1, 0] = 1

        if y1 == 0:
            x[x1, y1] = 1
        else:
            x[x1, y1] = df.iloc[x1, y1 - 1]


def sigmoid(x1):
    return 1 / (1 + np.power(np.e, -x1))


def h(num):
    return sigmoid((np.dot(p, x[num])))


def cost():
    h1 = np.zeros((150, 3))
    it = np.nditer(h1, op_flags=['readwrite'], flags=['multi_index'])
    for h2 in it:
        h1[it.multi_index[0]] = h(it.multi_index[0])
    return (.5 * np.sum(np.power((h1 - y), 2))) / 150


load_data()
print(h(3))
print(cost())
