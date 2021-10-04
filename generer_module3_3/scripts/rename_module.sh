# Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
#
# This file is part of aquiletour
#
# aquiletour is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# aquiletour is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

##### INCLUDE #####
this_dir=$(readlink -f $0)
scripts_dir=$(dirname "$this_dir")
. "$scripts_dir/include.sh"
###################

save_dir

echo -n "old module tag: "
read old_tag

echo -n "new module tag: "
read new_tag

echo "renaming $old_tag to $new_tag everywhere"

git commit --allow-empty -a -m"before renaming $old_tag to $new_tag"

# Gradle

sed s/$old_tag/$new_tag/g -i "$root_dir"/settings.gradle
sed s/$old_tag/$new_tag/g -i "$root_dir"/build.gradle


# Java

find "$src_tutoriels_dir" -type d -name "*$old_tag*" | while read old_package_dir
do

    # Renaming package directories
    old_package=$(basename "$old_package_dir")

    new_package=$(echo "$old_package" | sed s/$old_tag/$new_tag/)

    new_package_dir=$(dirname "$old_package_dir")/"$new_package"

    mv -v $old_package_dir $new_package_dir


    # Renaming files
    find "$new_package_dir" -type f -name "*$old_tag*.java" | while read old_java_path
    do

        old_java_file=$(basename $old_java_path)

        new_java_file=$(echo "$old_java_file" | sed s/$old_tag/$new_tag/)

        new_java_path=$(dirname $old_java_path)/"$new_java_file"

        mv -v "$old_java_path" "$new_java_path"
    done

    # Replacing within files
    find "$src_tutoriels_dir" -type f -name "*.java" | while read java_file_path
    do
        sed s/$old_tag/$new_tag/g -i "$java_file_path"
    done
done


git add "$root_dir"
git commit -m"after renaming $old_tag to $new_tag"




restore_dir

