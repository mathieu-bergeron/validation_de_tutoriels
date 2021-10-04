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

run_commands(){

    for command in "$@";
    do
        echo -n "[$current_repo]$ "
        echo $command
        eval "${command}"
        echo ""
    done

}

save_dir

cd "$root_dir"

submodules=$(git submodule | while read i; do echo $i | cut -d" " -f2; done)

for repo in $submodules; 
do

    cd $repo


    echo ""
    echo ""

    echo "========== ENTERING $repo  ============="

    sh scripts/run_commands.sh "$@"

    cd "$root_dir"

    echo "========== LEAVING $repo ============="

done

echo ""
echo "========= ROOT PROJECT ==========="

export current_repo=$(pwd)
run_commands "$@"

echo ""
echo ""
echo "========= ALL DONE ==========="

restore_dir

