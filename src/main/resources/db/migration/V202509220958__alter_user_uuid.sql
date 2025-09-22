BEGIN;
    ALTER TABLE public.users
    ALTER COLUMN uuid type uuid
          USING uuid::uuid;
COMMIT;