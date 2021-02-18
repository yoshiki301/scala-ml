# scala-ml

## git
ブランチ名はgitflowに準拠する。(https://qiita.com/KosukeSone/items/514dd24828b485c69a05)

issue1の新規機能に対応するブランチは`feature/1`と命名する。

```bash
$ git branch # 現在のブランチを確認
$ git branch feature/1 # 現在のブランチからfeature/1ブランチを派生させる
$ git checkout feature/1 # 現在のブランチからfeature/1ブランチに移る
```

`develop`ブランチから各種ブランチを生やしてcommit・pull requestする。コミットメッセージは以下のライト版を参考にする。(https://qiita.com/itosho/items/9565c6ad2ffc24c09364)

```bash
$ git add . # カレントディレクトリ以下を全てステージング
$ git commit -m "[summary] commit message" # ステージングしたファイルをコミットする, -m以下にコミットメッセージをかく
$ git push # 一通りコミットしたらリモートブランチにプッシュする
```

リモートの変更を取り込みたい時は`git pull`。
## docker
マウントしたいディレクトリのルートで以下のコマンドを実行する。以下の例では`/scala-dev`以下にファイルが共有される。
```bash
$ docker run -itv `pwd`:/scala-dev --rm hseeberger/scala-sbt:8u222_1.3.5_2.13.1
```

## docker-compose
`server`と`db`の2つのコンテナを立てるときに使う。
```bash
$ docker-compose up # イメージをプルしてコンテナを立てる
$ docker-compose exec server /bin/bash # scala-serverのシェルに入る
$ docker-compose down # 立てたコンテナを終了する
```

## version
ライブラリなどを追加する場合はここにバージョンを追記する。
```
sbt 1.3.5
scala 2.13.1
```