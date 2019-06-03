import pandas as pd
import glob
import numpy as np
import matplotlib.pyplot as plt 

def target(n=8):
    df = pd.DataFrame(columns=['target', 'nBig', 'nSolved', 'nAttempted'])
    for i in range(n):
        filenum = max(f.split('_')[2] for f in glob.glob(f'target_{i}_*'))
        df = df.add(pd.read_csv(f'target_{i}_{filenum}'), fill_value=0)

    df['target']=df['target']/n
    df['nBig']=df['nBig']/n
    df['success']=df['nSolved']/df['nAttempted']
    df['error']=(df['success']*(1-df['success'])/df['nAttempted'])**(.5)
    df['error']*=(1.96 / df['nAttempted'])

    return df
def main():
    df_target = target()
    a=np.zeros((5,900))
    for _, row in df_target.iterrows():
        # print(row)
        # print(row['target'], row['nBig'])
        a[int(row['nBig']), int(row['target'])-100]=row['success']

    fig = plt.figure()
    ax = plt.subplot(111)
    for i in [0,3,4,2,1,]:

        ax.plot(range(100,1000), a[i,:], label=f'{i} big numbers')

    ax.legend()
    plt.xlabel('Target')
    plt.ylabel('Probability of Being Solvable')
    plt.show()
    # print(df_target.sort_values('success'))
    # print(df_bigs)
if __name__=="__main__":
    main()