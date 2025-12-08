-- Add explicit constraint names, indexes and helper constraints for raw_materials and related tables

-- unique constraint for raw_materials (type + dimensions)
ALTER TABLE IF EXISTS raw_materials
  ADD CONSTRAINT IF NOT EXISTS raw_materials_uq_type_dims UNIQUE (raw_material_type_id, length, width, height);

-- Ensure foreign key constraints have explicit names (only create if not existing)
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.table_constraints tc
    WHERE tc.constraint_name = 'fk_rm_type') THEN
    ALTER TABLE raw_materials
      ADD CONSTRAINT fk_rm_type FOREIGN KEY (raw_material_type_id) REFERENCES raw_material_types(id);
  END IF;
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.table_constraints tc
    WHERE tc.constraint_name = 'fk_rm_component_list') THEN
    ALTER TABLE raw_materials
      ADD CONSTRAINT fk_rm_component_list FOREIGN KEY (component_list_id) REFERENCES component_lists(id);
  END IF;
END$$;

-- Indexes to speed up lookups by type + dimensions and by component_list
CREATE INDEX IF NOT EXISTS idx_raw_materials_type_dims ON raw_materials (raw_material_type_id, length, width, height);
CREATE INDEX IF NOT EXISTS idx_raw_materials_component_list ON raw_materials (component_list_id);

-- Foreign key index helpers
CREATE INDEX IF NOT EXISTS idx_front_element_furniture_body ON front_element(furniture_body_id);
CREATE INDEX IF NOT EXISTS idx_separator_parent ON separator(parent_id);
CREATE INDEX IF NOT EXISTS idx_component_lists_fb ON component_lists(furniture_body_id);

-- Optionally add updated_at trigger for raw_materials to keep updated_at current
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
  RETURN NEW;
END;
$$ language 'plpgsql';

DROP TRIGGER IF EXISTS trg_update_raw_materials_updated_at ON raw_materials;
CREATE TRIGGER trg_update_raw_materials_updated_at
BEFORE UPDATE ON raw_materials
FOR EACH ROW EXECUTE PROCEDURE update_updated_at_column();

-- end of migration
