import argparse
import os
import json

from sklearn.datasets import load_boston
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
import pandas as pd

import lightgbm as lgb

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--output_dir')
    parser.add_argument('--param_file')
    args = parser.parse_args()

    # 出力先ディレクトリの指定, なければ作成する
    output_dir = args.output_dir
    os.makedirs(output_dir, exist_ok=True)

    # パラメータファイルの読み込み, jsonを想定
    param_file = args.param_file
    with open(param_file, "r") as f:
        param_json = json.load(f)
    test_size = param_json["test_size"]
    random_state = param_json["random_state"]
    lgb_params = param_json["lgb_params"]

    # サンプルデータセットのロード
    boston = load_boston()
    X = pd.DataFrame(boston.data, columns=boston.feature_names)
    y = pd.Series(boston.target)

    # trainとtestに分割
    train_X, test_X, train_y, test_y = train_test_split(
        X, y, test_size=test_size, random_state=random_state
    )
    lgb_train = lgb.Dataset(train_X, train_y)

    # 学習
    model = lgb.train(lgb_params, lgb_train, verbose_eval=False)

    # 予測と予測精度の評価
    pred_y = model.predict(test_X)
    mse = mean_squared_error(test_y, pred_y)

    print(mse)