/*
 * #%L
 * Common package for I/O and related utilities
 * %%
 * Copyright (C) 2005 - 2016 Open Microscopy Environment:
 *   - Board of Regents of the University of Wisconsin-Madison
 *   - Glencoe Software, Inc.
 *   - University of Dundee
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package loci.common;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple writer for INI configuration files.
 *
 * @author Melissa Linkert melissa at glencoesoftware.com
 */
public class IniWriter {

  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(IniWriter.class);

  // -- IniWriter API methods --

  /**
   * Saves the given IniList to the given file.
   * If the given file already exists, then the IniList will be appended.
   *
   * @param ini the {@link IniList} to be written
   * @param path the path to a writable file on disk
   * @throws IOException if there is an error during writing
   */
  public void saveINI(IniList ini, String path) throws IOException {
    saveINI(ini, path, true);
  }

  /**
   * Saves the given IniList to the given file.
   *
   * @param ini the {@link IniList} to be written
   * @param path the path to a writable file on disk
   * @param append true if the INI data should be appended to
   *               the end of the file if it already exists
   * @param sorted true if the INI keys should be sorted before writing
   * @throws IOException if there is an error during writing
   */
  public void saveINI(IniList ini, String path, boolean append, boolean sorted)
    throws IOException
  {
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
      new FileOutputStream(path, append), Constants.ENCODING));

    for (IniTable table : ini) {

      String header = table.get(IniTable.HEADER_KEY);
      out.write("[" + header + "]\n");
      Set<String> keys;
      if (sorted) {
        Map<String, String> treeMap = new TreeMap<String, String>(table);
        keys = treeMap.keySet();
      }
      else {
        keys = table.keySet();
      }

      for (String key : keys) {
        out.write(key + " = " + table.get(key) + "\n");
      }
      out.write("\n");
    }

    out.close();
  }

  /**
   * Saves the given IniList to the given file.
   *
   * @param ini the {@link IniList} to be written
   * @param path the path to a writable file on disk
   * @param append true if the INI data should be appended to
   *               the end of the file if it already exists
   * @throws IOException if there is an error during writing
   */
  public void saveINI(IniList ini, String path, boolean append)
    throws IOException
  {
    saveINI(ini, path, append, false);
  }

}
