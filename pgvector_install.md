# PG Vector Installation

## Anaconda
Download and install Anacoda from: https://www.anaconda.com/download

Because I use zsh on linux (I believe you also have to do this on Mac OS, since it also defaults to zsh now)
```
conda init zsh
```

Once Anacoda is installed, open a new shell (which should now have conda configuration included)
Execute the following commands to install postgresql with pgvector

```sh
conda create -n myenv  
conda activate myenv   
conda install conda-forge::postgresql  
conda install conda-forge::pgvector 
```

## Start Postgresql
Make sure you don't already have a postgresql database running
Then do the following to create and enter the database

```sh
initdb -D dbfiles 
pg_ctl -D dbfiles -l logfile start 
createdb embeddings
createuser --pwprompt db_user
psql -d embeddings  
```

## Create a vector table
Then on the postgresql prompt execute:

```sql
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store (
	id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
	content text,
	metadata json,
	embedding vector(768) -- 1536 is the default embedding dimension
);

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);
\q
```
