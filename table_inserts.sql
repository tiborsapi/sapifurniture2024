-- Create sequence for furniture_body primary key
CREATE SEQUENCE pk_furniture_body
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create furniture_body table
CREATE TABLE public.furniture_body (
    id BIGINT NOT NULL DEFAULT nextval('pk_furniture_body'),
    width INTEGER,
    heigth INTEGER,  -- Note: "heigth" is kept as is from your model, though "height" would be the correct spelling
    depth INTEGER,

    -- Primary key constraint
    CONSTRAINT furniture_body_pkey PRIMARY KEY (id)
);

-- Add table comment
COMMENT ON TABLE public.furniture_body IS 'Stores information about furniture body dimensions';

-- Add column comments
COMMENT ON COLUMN public.furniture_body.id IS 'Primary key';
COMMENT ON COLUMN public.furniture_body.width IS 'Width of the furniture body';
COMMENT ON COLUMN public.furniture_body.heigth IS 'Height of the furniture body';
COMMENT ON COLUMN public.furniture_body.depth IS 'Depth of the furniture body';

-- Optional: Add some basic constraints to ensure positive dimensions
ALTER TABLE public.furniture_body
    ADD CONSTRAINT check_positive_dimensions
    CHECK (width > 0 AND heigth > 0 AND depth > 0);

-- Create enum type for drawer status
CREATE TYPE drawer_status AS ENUM ('FUNCTIONAL', 'DAMAGED', 'UNDER_REPAIR', 'DECOMMISSIONED');

-- Create the drawer table
CREATE TABLE public.drawer (
    id SERIAL PRIMARY KEY,
    material VARCHAR(50) NOT NULL,
    color VARCHAR(30) NOT NULL,
    height DECIMAL(10,2) NOT NULL CHECK (height > 0),
    width DECIMAL(10,2) NOT NULL CHECK (width > 0),
    depth DECIMAL(10,2) NOT NULL CHECK (depth > 0),
    weight DECIMAL(10,2) NOT NULL CHECK (weight > 0),
    is_open BOOLEAN NOT NULL DEFAULT FALSE,
    max_open_distance DECIMAL(10,2) NOT NULL CHECK (max_open_distance > 0),
    current_open_distance DECIMAL(10,2) NOT NULL CHECK (current_open_distance >= 0),
    weight_capacity DECIMAL(10,2) NOT NULL CHECK (weight_capacity > 0),
    furniture_body_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT DEFAULT 0,
    description VARCHAR(500),
    status drawer_status NOT NULL DEFAULT 'FUNCTIONAL',

    -- Constraints
    CONSTRAINT fk_drawer_furniture_body
        FOREIGN KEY (furniture_body_id)
        REFERENCES public.furniture_body(id),
    CONSTRAINT check_current_open_distance
        CHECK (current_open_distance <= max_open_distance),
    CONSTRAINT check_weight
        CHECK (weight <= weight_capacity)
);

-- Create indexes
CREATE INDEX idx_drawer_material ON public.drawer(material);
CREATE INDEX idx_drawer_furniture_body ON public.drawer(furniture_body_id);

-- Create trigger for updated_at timestamp
CREATE OR REPLACE FUNCTION update_drawer_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_drawer_timestamp
    BEFORE UPDATE ON drawer
    FOR EACH ROW
    EXECUTE FUNCTION update_drawer_timestamp();

-- Add table comment
COMMENT ON TABLE public.drawer IS 'Stores information about furniture drawers';

-- Add column comments
COMMENT ON COLUMN public.drawer.id IS 'Primary key';
COMMENT ON COLUMN public.drawer.material IS 'Material of the drawer';
COMMENT ON COLUMN public.drawer.color IS 'Color of the drawer';
COMMENT ON COLUMN public.drawer.height IS 'Height in centimeters';
COMMENT ON COLUMN public.drawer.width IS 'Width in centimeters';
COMMENT ON COLUMN public.drawer.depth IS 'Depth in centimeters';
COMMENT ON COLUMN public.drawer.weight IS 'Weight in kilograms';
COMMENT ON COLUMN public.drawer.is_open IS 'Current state of the drawer (open/closed)';
COMMENT ON COLUMN public.drawer.max_open_distance IS 'Maximum distance the drawer can open in centimeters';
COMMENT ON COLUMN public.drawer.current_open_distance IS 'Current open distance in centimeters';
COMMENT ON COLUMN public.drawer.weight_capacity IS 'Maximum weight capacity in kilograms';
COMMENT ON COLUMN public.drawer.furniture_body_id IS 'Reference to the furniture body this drawer belongs to';
COMMENT ON COLUMN public.drawer.status IS 'Current operational status of the drawer';

