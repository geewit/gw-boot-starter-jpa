package io.geewit.boot.autoconfigure.orm.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Settings to apply when configuring Hibernate.
 *
 * @author Andy Wilkinson
 * @since 2.0.0
 */
public class HibernateSettings {

    private Supplier<String> ddlAuto;

    private Collection<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers;

    public HibernateSettings ddlAuto(Supplier<String> ddlAuto) {
        this.ddlAuto = ddlAuto;
        return this;
    }

    /**
     * Specify the default ddl auto value to use.
     * @param ddlAuto the default ddl auto if none is provided
     * @return this instance
     * @see #ddlAuto(Supplier)
     * @deprecated as of 2.0.1 in favour of {@link #ddlAuto(Supplier)}
     */
    @Deprecated
    public HibernateSettings ddlAuto(String ddlAuto) {
        return ddlAuto(() -> ddlAuto);
    }

    public String getDdlAuto() {
        return (this.ddlAuto != null ? this.ddlAuto.get() : null);
    }

    public HibernateSettings hibernatePropertiesCustomizers(
            Collection<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        this.hibernatePropertiesCustomizers = new ArrayList<>(
                hibernatePropertiesCustomizers);
        return this;
    }

    public Collection<HibernatePropertiesCustomizer> getHibernatePropertiesCustomizers() {
        return this.hibernatePropertiesCustomizers;
    }

}
