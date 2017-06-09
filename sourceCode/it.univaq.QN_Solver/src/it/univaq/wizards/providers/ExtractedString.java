package it.univaq.wizards.providers;

/**
 * The key/value pair of a string to be extracted
 */
public class ExtractedString
{
   private String name;
   private String description;

   public ExtractedString(String name, String description) {
      this.name = name;
      this.description = description;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }
}
